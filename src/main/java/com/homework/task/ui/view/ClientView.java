package com.homework.task.ui.view;

import com.homework.task.entities.Client;
import com.homework.task.service.ClientService;
import com.homework.task.ui.component.ClientComponent;
import com.homework.task.ui.main.MainView;
import com.homework.task.ui.util.Notice;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;

@UIScope
@SpringComponent
@PageTitle("Клиенты")
@RouteAlias(value = "", layout = MainView.class)
@Route(value = "/client", layout = MainView.class)
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ClientView extends VerticalLayout {

    private final ClientService clientService;

    private final ClientComponent editor;

    private Grid<Client> grid;

    private Button add;
    private Button edit;
    private Button delete;

    private TextField search;

    private Notice notice;

    public ClientView(ClientService clientService, ClientComponent editor) {
        this.clientService = clientService;
        this.editor = editor;
    }

    @PostConstruct
    private void initialization() {
        grid = new Grid<>(Client.class, false);

        add = new Button("Добавить", VaadinIcon.ADD_DOCK.create());
        edit = new Button("Редактировать", VaadinIcon.EDIT.create());
        delete = new Button("Удалить", VaadinIcon.TRASH.create());

        search = new TextField();
        search.setPlaceholder("Введите имя...");
        search.setPrefixComponent(VaadinIcon.SEARCH.create());
        search.setClearButtonVisible(true);

        grid.setItems(clientService.findAll());

        notice = new Notice();

        edit.setEnabled(false);
        delete.setEnabled(false);

        HorizontalLayout leftSide = new HorizontalLayout(search);

        setHorizontalComponentAlignment(Alignment.END, leftSide);

        HorizontalLayout rightSide = new HorizontalLayout(add, edit, delete);
        setHorizontalComponentAlignment(Alignment.START, rightSide);

        HorizontalLayout general = new HorizontalLayout(rightSide, leftSide);


        initializationGrid();
        initializationButtons();
        filterClient();

        add(general, grid);
    }

    private void initializationGrid() {
        grid.addColumn(Client::getName).setHeader("Имя").setSortable(true);
        grid.addColumn(Client::getSurname).setHeader("Фамилия").setSortable(true);
        grid.addColumn(Client::getPatronymic).setHeader("Отчество").setSortable(true);
        grid.addColumn(Client::getPassport).setHeader("Паспорт").setSortable(true);
        grid.addColumn(Client::getEmail).setHeader("Email").setSortable(true);
        grid.addColumn(Client::getNumber).setHeader("Номер телефона").setSortable(true);
    }

    private void initializationButtons() {
        add.addClickListener(click -> {
            editor.open();
            editor.edit(new Client());
        });

        edit.addClickListener(click -> {
            editor.open();
            editor.edit(editor.getClient());
        });

        delete.addClickListener(click -> {
            notice.setSuccessNotice("Успешно!");
            clientService.delete(editor.getClient());
            refresh();
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
            editor.open();
            editor.edit(doubleClick.getItem());
        });
    }

    private void filterClient() {
        search.setValueChangeMode(ValueChangeMode.EAGER);
        search.addValueChangeListener(change -> showClients(change.getValue()));
    }

    private void showClients(String filter) {
        if (filter.isEmpty()) {
            grid.setItems(clientService.findAll());
        } else {
            grid.setItems(clientService.findAll(filter));
        }
    }

    public void refresh() {
        grid.setItems(clientService.findAll());
    }
}
