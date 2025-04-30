package mai.geomod.kosscad.dxf;

import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import mai.geomod.kosscad.util.WorkSpace;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class DXFReader {
    private final WorkSpace workspace;
    private File file;
    private final FigureReader figureReader;

    public DXFReader(WorkSpace space) {
        workspace = space;
        figureReader = new FigureReader(space);
    }

    public void open() {
        fileInit();
        if (file != null) {
            try {
                workspace.clear();
                readDxfFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void fileInit() {
        Stage stage = (Stage) workspace.getWorkSpace().getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Открытие файла");

        File defaultDirectory = new File("C:/МАИ/Геометрическое моделирование");
        if (defaultDirectory.exists())
            fileChooser.setInitialDirectory(defaultDirectory);

        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Файл DXF", "*.dxf")
        );
        file = fileChooser.showOpenDialog(stage);
    }

    private void readDxfFile() throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            figureReader.read(reader);
        }
    }
}
