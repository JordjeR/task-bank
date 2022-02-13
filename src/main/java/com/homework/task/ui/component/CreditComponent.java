package com.homework.task.ui.component;

import com.homework.task.entities.Credit;
import com.homework.task.service.CreditService;
import com.homework.task.ui.util.Notice;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;

import javax.annotation.PostConstruct;
import java.util.Objects;
import java.util.Optional;

@UIScope
@SpringComponent
public class CreditComponent {

    private final CreditService creditService;

    private Dialog dialog;

    private NumberField limit;
    private NumberField interest;

    private Button ok;
    private Button cancel;

    private Binder<Credit> binder;

    private Notice notice;

    private Credit credit;

    public CreditComponent(CreditService creditService) {
        this.creditService = creditService;
    }

    @PostConstruct
    private void initialization() {
        dialog = new Dialog();

        limit = new NumberField("Кредитный лимит");
        interest = new NumberField("Ставка");

        ok = new Button("Ok!", VaadinIcon.CHECK.create());
        cancel = new Button("Cancel", VaadinIcon.BAN.create());

        notice = new Notice();

        binder = new Binder<>(Credit.class);

        limit.setPrefixComponent(VaadinIcon.DOLLAR.create());
        interest.setPrefixComponent(VaadinIcon.CASH.create());

        initializationDialogWindow();
        validationFields();
        eventsButtons();
    }

    private void initializationDialogWindow() {
        H2 title = new H2("Add or Edit");

        VerticalLayout fields = new VerticalLayout();

        fields.add(
                title,
                limit,
                interest
        );

        HorizontalLayout buttons = new HorizontalLayout();

        buttons.add(
                ok,
                cancel
        );

        dialog.add(fields, buttons);
    }

    private void eventsButtons() {
        ok.addClickListener(click -> {
            save();
            notice.setSuccessNotice("Успешно!");
            UI.getCurrent().getPage().reload();
        });

        cancel.addClickListener(click -> dialog.close());
    }

    private void validationFields() {
        binder.forField(limit)
                .withValidator(Objects::nonNull, "Введите значение")
                .withValidator(v -> v >= 1000 && v <= 5000000, "Сумма в пределах 1000 и 5000000")
                .bind(Credit::getCreditLimit, Credit::setCreditLimit);

        binder.forField(interest)
                .withValidator(Objects::nonNull, "Введите значение")
                .withValidator(v -> v >= 0.1 && v <= 100.0, "Некорректная процентная ставка (0.1 - 100%)")
                .bind(Credit::getInterestRate, Credit::setInterestRate);

        binder.setBean(credit);
    }

    private void save() {
        if (!binder.validate().hasErrors()) {
            creditService.save(credit);
        }
    }

    public void edit(Credit changedCredit) {
        if (changedCredit == null) {
            return;
        }

        if (changedCredit.getId() != null) {
            Optional<Credit> credit = creditService.findById(changedCredit.getId());

            Credit temp = new Credit();

            if (credit.isPresent()) {
                temp = credit.get();
            }

            this.credit = temp;

        } else {
            this.credit = changedCredit;
        }

        binder.setBean(credit);
    }

    public void open() {
        dialog.open();
    }

    public Dialog getDialog() {
        return dialog;
    }

    public Credit getCredit() {
        return credit;
    }
}