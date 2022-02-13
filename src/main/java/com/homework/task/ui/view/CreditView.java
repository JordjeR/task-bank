package com.homework.task.ui.view;

import com.homework.task.entities.Credit;
import com.homework.task.service.CreditService;
import com.homework.task.ui.component.CreditComponent;
import com.homework.task.ui.main.MainView;
import com.homework.task.ui.util.Notice;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
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
@Route(value = "/credit", layout = MainView.class)
@PageTitle("Кредиты")
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class CreditView extends VerticalLayout {

    private final CreditService creditService;

    private final CreditComponent editor;

    private Grid<Credit> grid;

    private Button add;
    private Button delete;
    private Button edit;

    private Notice notice;

    public CreditView(CreditService creditService, CreditComponent editor) {
        this.creditService = creditService;
        this.editor = editor;
    }

    @PostConstruct
    private void initialization() {
        grid = new Grid<>(Credit.class, false);

        add = new Button("Добавить", VaadinIcon.ADD_DOCK.create());
        edit = new Button("Изменить", VaadinIcon.EDIT.create());
        delete = new Button("Аннулировать", VaadinIcon.TRASH.create());

        delete.setEnabled(false);
        edit.setEnabled(false);

        grid.setItems(creditService.findAll());

        notice = new Notice();

        initializationGrid();
        initializationButtons();
        HorizontalLayout rightSide = new HorizontalLayout(add, edit, delete);

        add(rightSide, grid);
    }

    private void initializationGrid() {
        grid.addColumn(Credit::getCreditLimit).setHeader("Лимит по кредиту").setSortable(true);
        grid.addColumn(Credit::getInterestRate).setHeader("Процент").setSortable(true);
    }

    private void initializationButtons() {
        add.addClickListener(click -> {
            editor.getDialog().open();
            editor.edit(new Credit());
        });

        delete.addClickListener(click -> {
            notice.setSuccessNotice("Успешно!");
            creditService.delete(editor.getCredit());
            refresh();
        });

        edit.addClickListener(click -> {
            editor.open();
            editor.edit(editor.getCredit());
        });

        grid.asSingleSelect().addValueChangeListener(choose -> {
            editor.edit(choose.getValue());
        });

        grid.addSelectionListener(selected -> {
            boolean isSelected = !grid.getSelectedItems().isEmpty();
            edit.setEnabled(isSelected);
            delete.setEnabled(isSelected);
        });

        grid.addItemDoubleClickListener(doubleClick -> {
            editor.getDialog().open();
            editor.edit(doubleClick.getItem());
        });
    }

    public void refresh() {
        grid.setItems(creditService.findAll());
    }
}
