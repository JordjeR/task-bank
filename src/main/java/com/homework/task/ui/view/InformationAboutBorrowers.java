package com.homework.task.ui.view;

import com.homework.task.entities.CreditOffer;
import com.homework.task.service.CreditOfferService;
import com.homework.task.ui.main.MainView;
import com.homework.task.ui.util.Notice;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;

@UIScope
@SpringComponent
@Route(value = "/credit-offers", layout = MainView.class)
@PageTitle("Кредиты")
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class InformationAboutBorrowers extends VerticalLayout {

    private final CreditOfferService creditOfferService;

    private Grid<CreditOffer> grid;

    private Button delete;
    private Button prev;

    private H2 title;

    private Notice notice;

    private CreditOffer creditOffer;

    public InformationAboutBorrowers(CreditOfferService creditOfferService) {
        this.creditOfferService = creditOfferService;
    }

    @PostConstruct
    private void initialization() {
        grid = new Grid<>(CreditOffer.class, false);

        title = new H2("Клиенты с кредитами");

        delete = new Button("Удалить", VaadinIcon.TRASH.create());
        prev = new Button("Назад", VaadinIcon.ARROW_LEFT.create());

        delete.setEnabled(false);

        grid.setItems(creditOfferService.findAll());

        notice = new Notice();

        initGrid();
        eventsButtons();

        HorizontalLayout buttons = new HorizontalLayout(prev, delete);

        add(title, buttons, grid);
    }

    private void eventsButtons() {
        grid.asSingleSelect().addValueChangeListener(click -> {
            creditOffer = click.getValue();
        });

        grid.addSelectionListener(selected -> {
            boolean isSelected = !grid.getSelectedItems().isEmpty();
            delete.setEnabled(isSelected);
        });

        delete.addClickListener(click -> {
            creditOfferService.delete(getCreditOffer());
            notice.setSuccessNotice("Успешно!");
            refresh();
        });

        prev.addClickListener(click -> {
            UI.getCurrent().navigate(CreditOfferView.class);
        });
    }

    private void initGrid() {
        grid.addColumn(CreditOffer::getClient).setHeader("Клиент");
        grid.addColumn(CreditOffer::getCredit).setHeader("Допустимый лимит и процентная ставка");
        grid.addColumn(CreditOffer::getAmountOfCredit).setHeader("Сумма займа");
    }

    private void refresh() {
        grid.setItems(creditOfferService.findAll());
    }

    public CreditOffer getCreditOffer() {
        return creditOffer;
    }

    public void setCreditOffer(CreditOffer creditOffer) {
        this.creditOffer = creditOffer;
    }
}
