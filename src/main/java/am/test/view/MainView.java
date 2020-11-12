package am.test.view;

import am.test.parser.ParserService;
import am.test.service.CompareService;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.radiobutton.RadioGroupVariant;
import com.vaadin.flow.router.Route;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by
 * Mher Petrosyan
 * Email mher13.02.94@gmail.ru
 */
@Route
@Service
public class MainView extends VerticalLayout {

    private final Button compare;
    private final RadioButtonGroup<String> radioGroup;
    private final CompareService compareService;

    public MainView(CompareService compareService, ParserService parserService) {
        this.compareService = compareService;

        Button db = new Button("Replenish the Database");
        db.addClickListener(e -> {
            parserService.setup();
            try {
                parserService.saveNames();
            } catch (InterruptedException interruptedException) {
                add("Problems during work parser");
            }

        });
        radioGroup = new RadioButtonGroup<>();
        radioGroup.setLabel("Type");
        radioGroup.setItems("Names", "Surnames");
        radioGroup.addThemeVariants(RadioGroupVariant.LUMO_VERTICAL);

        compare = new Button("Compare");
        add(db, radioGroup, compare);
        giveResult();

    }

    public void giveResult() {

        compare.addClickListener(e -> {

            String value = radioGroup.getValue();
            if (value != null) {
                if (value.equals("Names")) {
                    List<String> compareNames = compareService.compareName();
                    Grid<String> gridNames = new Grid<>();
                    gridNames.addColumn(String::toString).setHeader("NAMES");
                    gridNames.setItems(compareNames);
                    d("Compare male names with female names", gridNames);
                } else {
                    List<String> compareSurnames = compareService.compareSurname();
                    Grid<String> gridSurnames = new Grid<>();
                    gridSurnames.addColumn(String::toString).setHeader("SURNAMES");
                    gridSurnames.setItems(compareSurnames);
                    d("Compare male surnames with female surnames", gridSurnames);
                }
            }
        });
    }

    public void d(String text, Grid<String> grid) {
        Dialog dialog = new Dialog();
        dialog.add(new Text(text), grid);

        dialog.setWidth("1200px");
        dialog.setHeight("600px");

        dialog.open();
    }
}