import org.testfx.api.FxRobot;

import javafx.scene.Node;
import javafx.scene.control.Labeled;
import javafx.scene.control.TableView;
import org.testfx.service.query.NodeQuery;

public final class Table {

    private final FxRobot fxRobot;
    private final String locator;

    public Table(final FxRobot fxRobot, final String locator) {
        this.fxRobot = fxRobot;
        this.locator = locator;
    }

    public void clickOnNthHeader(final int nth) {
        final Node headerNode = fxRobot.lookup(locator).lookup(".column-header").nth(nth).queryAs(Node.class);
        fxRobot.clickOn(headerNode);
    }

    public String getNthTextInFirstRow(final int nth) {
        final Labeled labeled = fxRobot.lookup(locator).lookup(".table-cell").nth(nth).queryLabeled();
        return labeled.getText();
    }

    public void clickOnNthElementInFirstRow(final int nth) {
        final Node node = fxRobot.lookup(locator).lookup(".table-cell").nth(nth).query();
        fxRobot.clickOn(node);
    }

    public String getTextOnNthTableRowNthTableColum(final int nth, int nthColum) {
        Labeled label = fxRobot.lookup(locator).lookup(".table-row-cell").nth(nth).lookup(".table-cell").nth(nthColum).queryLabeled();
        return label.getText();
    }

    public void clickOnNthTableRow(final int nth) {
        final Node node = fxRobot.lookup(locator).lookup(".table-row-cell").nth(nth).query().lookup(".table-cell");
        fxRobot.clickOn(node);
    }

    public int countItems() {
        final TableView<Object> tableView = fxRobot.lookup(locator).queryTableView();
        return tableView.getItems().size();
    }

}
