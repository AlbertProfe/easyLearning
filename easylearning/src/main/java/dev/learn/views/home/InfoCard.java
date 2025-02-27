package dev.learn.views.home;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;

public class InfoCard extends Composite<Div> {
    private final H3 title = new H3();
    private final Paragraph description = new Paragraph();
    private final Image image = new Image();

    public InfoCard(String titleText, String descriptionText, String imagePath) {
        title.setText(titleText);
        description.setText(descriptionText);
        image.setSrc(imagePath);
        image.setAlt(titleText);

        Div content = getContent();
        content.addClassNames("info-card", "bg-contrast-5", "rounded-l", "p-m");
        content.add(image, title, description);
    }
}
