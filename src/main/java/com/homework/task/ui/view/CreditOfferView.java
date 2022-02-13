package com.homework.task.ui.view;

import com.homework.task.entities.Client;
import com.homework.task.entities.Credit;
import com.homework.task.entities.CreditOffer;
import com.homework.task.service.ClientService;
import com.homework.task.service.CreditOfferService;
import com.homework.task.service.CreditService;
import com.homework.task.ui.main.MainView;
import com.homework.task.ui.util.Notice;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

@SpringComponent
@UIScope
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@PageTitle("Информация о кредите")
@Route(value = "/all-information", layout = MainView.class)
public class CreditOfferView extends VerticalLayout {

    private Select<Client> client;
    private Select<Credit> credit;
    private NumberField sumCredit;
    private NumberField creditTerm;
    private DatePicker datePayment;

    private Button loan;
    private Button next;

    private Binder<CreditOffer> binder;

    private CreditOffer offer;

    private final CreditOfferService creditOfferService;
    private final ClientService clientService;
    private final CreditService creditService;

    private Notice notice;

    private Grid<CreditOffer> grid;

    private Label sumPayment;

    public CreditOfferView(
            CreditOfferService creditOfferService,
            ClientService clientService,
            CreditService creditService) {
        this.creditOfferService = creditOfferService;
        this.clientService = clientService;
        this.creditService = creditService;
    }

    @PostConstruct
    private void initialization() {

        Locale locale = new Locale("fi", "FI");

        offer = new CreditOffer();

        notice = new Notice();

        client = new Select<>();
        credit = new Select<>();
        sumCredit = new NumberField("Сумма кредита");

        creditTerm = new NumberField("Срок кредита (мес.)");
        creditTerm.setWidth("250px");

        datePayment = new DatePicker("Дата ежемесячного платежа");
        datePayment.setWidth("300px");

        client.setLabel("Клиент");
        client.setPlaceholder("Выберите клиента");
        client.setWidth("300px");
        client.setItemLabelGenerator(Client::toString);

        List<Client> clients = clientService.findAll();
        client.setItems(clients);

        credit.setLabel("Кредит");
        credit.setPlaceholder("Выберите кредит");
        credit.setItemLabelGenerator(Credit::toString);

        List<Credit> credits = creditService.findAll();
        credit.setItems(credits);

        datePayment.setLocale(locale);
        datePayment.setPlaceholder("DD.MM.YYYY");

        loan = new Button("Оформить", VaadinIcon.CHECK.create());
        next = new Button("Информация о заёмщиках", VaadinIcon.ARROW_RIGHT.create());

        sumCredit.setPrefixComponent(VaadinIcon.DOLLAR.create());
        creditTerm.setPrefixComponent(VaadinIcon.TIMER.create());

        grid = new Grid<>(CreditOffer.class, false);

        grid.setVisible(false);

        sumPayment = new Label();

        sumPayment.setVisible(false);

        binder = new Binder<>(CreditOffer.class);

        initializationFields();
        validationFields();
        eventsButtons();

        initializationGridAllView();
    }

    private void eventsButtons() {
        loan.addClickListener(click -> {
            save();
            visibleComponents();
            notice.setSuccessNotice("Кредит успешно оформлен!");
        });

        next.addClickListener(click -> UI.getCurrent().navigate(InformationAboutBorrowers.class));
    }

    private void validationFields() {
        binder.forField(client).asRequired().bind(CreditOffer::getClient, CreditOffer::setClient);
        binder.forField(credit).asRequired().bind(CreditOffer::getCredit, CreditOffer::setCredit);

        binder.forField(sumCredit)
                .withValidator(Objects::nonNull, "Введите значение")
                .withValidator(v -> v <= credit.getValue().getCreditLimit(), "Сумма выше лимита")
                .asRequired()
                .bind(CreditOffer::getAmountOfCredit, CreditOffer::setAmountOfCredit);

        creditTerm.setMax(60);
        creditTerm.setMin(12);

        creditTerm.setValueChangeMode(ValueChangeMode.EAGER);
        creditTerm.addValueChangeListener(listen -> {
            if (listen.getValue() < 12 || listen.getValue() > 60) {
                creditTerm.setErrorMessage("Выберете срок от 12 до 60 мес.");
            }
        });

        binder.forField(datePayment)
                .withValidator(Objects::nonNull, "Выберите дату")
                .withValidator(v -> {
                    int year = v.getYear();
                    return year <= 2022;
                }, "Выберите корректную дату")
                .asRequired()
                .bind(CreditOffer::getDatePayment, CreditOffer::setDatePayment);

        binder.setBean(offer);
    }

    private void visibleComponents() {
        grid.setItems(creditOfferService.calculatePaymentSchedule(getOffer(), getCreditTerm()));
        grid.setVisible(true);

        sumPayment.setText("Сумма платежа: " +
                creditOfferService.getMonthlyPayment(getOffer(), getCreditTerm()).toString());

        sumPayment.setVisible(true);

        add(sumPayment, grid);
    }

    private void initializationFields() {
        H2 title = new H2("Заполнение данных");

        HorizontalLayout fields = new HorizontalLayout();

        fields.setSpacing(true);

        fields.add(
                client,
                credit,
                sumCredit,
                creditTerm,
                datePayment
        );

        HorizontalLayout buttons = new HorizontalLayout();

        buttons.add(
                loan,
                next
        );

        VerticalLayout general = new VerticalLayout();

        general.add(
                title,
                fields,
                buttons
        );

        add(general);
    }

    private void initializationGridAllView() {
        grid.addColumn(CreditOffer::getSumPayment).setHeader("Платеж").setSortable(true);
        grid.addColumn(CreditOffer::getSumRepayCredit).setHeader("Долговая часть").setSortable(true);
        grid.addColumn(CreditOffer::getSumRepayPercent).setHeader("Процентная часть").setSortable(true);
    }

    public void save() {
        if (!binder.validate().hasErrors()) {
            creditOfferService.save(offer);
        }
    }

    public Double getCreditTerm() {
        return creditTerm.getValue();
    }

    public void setOffer(CreditOffer offer) {
        this.offer = offer;
    }

    public CreditOffer getOffer() {
        return offer;
    }
}