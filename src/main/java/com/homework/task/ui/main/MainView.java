package com.homework.task.ui.main;

import com.homework.task.ui.view.ClientView;
import com.homework.task.ui.view.CreditOfferView;
import com.homework.task.ui.view.CreditView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.tabs.TabsVariant;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

@UIScope
@SpringComponent
@PageTitle("Главная страница")
@Theme(variant = Lumo.DARK, value = Lumo.class)
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class MainView extends AppLayout {

    @Autowired
    public MainView() {

        DrawerToggle toggle = new DrawerToggle();

        H3 title = new H3("Bank operations");

        Tabs tabs = getTabs();

        tabs.addSelectedChangeListener(viewSelected -> {
            UI.getCurrent().navigate(viewSelected.getSelectedTab().getLabel());
        });

        addToDrawer(tabs);
        addToNavbar(toggle, title);
    }

    public Tabs getTabs() {
        Tabs tabs = new Tabs();

        tabs.add(
                createTab("Клиенты", ClientView.class),
                createTab("Кредиты", CreditView.class),
                createTab("Оформление кредита", CreditOfferView.class)
        );

        tabs.setOrientation(Tabs.Orientation.VERTICAL);

        tabs.addThemeVariants(TabsVariant.LUMO_ICON_ON_TOP);
        tabs.setSelectedIndex(0);
        tabs.setAutoselect(true);

        return tabs;
    }

    public Tab createTab(String nameTab, Class view) {
        RouterLink link = new RouterLink();
        link.add(new Span(nameTab));
        link.setRoute(view);

        return new Tab(link);
    }
}