package sample;


import javafx.fxml.FXML;
import javafx.scene.control.ColorPicker;
import net.sourceforge.jFuzzyLogic.FIS;
import net.sourceforge.jFuzzyLogic.FunctionBlock;
import net.sourceforge.jFuzzyLogic.Gpr;
import net.sourceforge.jFuzzyLogic.plot.JFuzzyChart;
import net.sourceforge.jFuzzyLogic.rule.Variable;

import java.sql.PreparedStatement;

public class Controller {

    @FXML
    ColorPicker colorPicker = new ColorPicker();


    @FXML
    public void fuzzy(){


        // Load from 'FCL' file
        String fileName = "src/sample/fuzzycolor.fcl";
        FIS fis = FIS.load(fileName,true);

        // Error while loading?
        if( fis == null ) {
            System.err.println("Can't load file: '" + fileName + "'");
            return;
        }

        // Show ruleset
        FunctionBlock functionBlock = fis.getFunctionBlock(null);
        JFuzzyChart.get().chart(functionBlock);

        // Set inputs


        double rojo = colorPicker.getValue().getRed() * 10;
        double verde = colorPicker.getValue().getGreen() * 10;
        double azul = colorPicker.getValue().getBlue() * 10;

        System.out.println("ROJO: " + rojo + " VERDE: " + verde + " AZUL " + azul);

        functionBlock.setVariable("red", rojo);
        functionBlock.setVariable("green", verde);
        functionBlock.setVariable("blue", azul);

        // Evaluate
        functionBlock.evaluate();

        // Show output variable's chart
        Variable tip = functionBlock.getVariable("color");
        JFuzzyChart.get().chart(tip, tip.getDefuzzifier(), true);
        Gpr.debug("poor[service]: " + functionBlock.getVariable("red").getMembership("low"));

        // Print ruleSet
        System.out.println(functionBlock);
        System.out.println("TIP:" + functionBlock.getVariable("color").getValue());


        // Print ruleSet
        System.out.println(fis);

    }

}
