package dev.learn.views.home;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.theme.lumo.LumoUtility.Margin;
import org.vaadin.lineawesome.LineAwesomeIconUrl;

@PageTitle("Home")
@Route("")
@Menu(order = 0, icon = LineAwesomeIconUrl.FILE)
@AnonymousAllowed
public class HomeView extends VerticalLayout {
    public HomeView() {
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        H1 welcome = new H1("Welcome to Easy Learning");
        welcome.addClassNames("text-l", "mb-m");

        Image img = new Image("images/empty-plant.png", "placeholder plant");
        img.setWidth("200px");
        add(img);

        HorizontalLayout cardLayout = new HorizontalLayout();
        cardLayout.setWidthFull();
        cardLayout.setJustifyContentMode(JustifyContentMode.CENTER);

        InfoCard codeCard = new InfoCard(
                "Code Learning",
                "Master programming concepts through interactive quizzes and hands-on exercises.",
                "images/code-learning.png"
        );

        InfoCard mindsetCard = new InfoCard(
                "Growth Mindset",
                "Develop a positive learning attitude to overcome challenges in your coding journey.",
                "images/mindset.png"
        );

        InfoCard devopsCard = new InfoCard(
                "DevOps Essentials",
                "Learn DevOps practices and tools to streamline your development workflow.",
                "images/devops.png"
        );

        cardLayout.add(codeCard, mindsetCard, devopsCard);

        Button startQuizButton = new Button("Start Quiz");
        startQuizButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        startQuizButton.addClickListener(e -> getUI().ifPresent(ui -> ui.navigate("quizLearning")));
        add(welcome, cardLayout, startQuizButton);
    }
}



