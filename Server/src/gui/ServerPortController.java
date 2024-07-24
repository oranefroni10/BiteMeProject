package gui;

import java.net.InetAddress;
import java.net.UnknownHostException;

import Server.EchoServer;
import db.DBConnectionDetails;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ServerPortController {

	@FXML
	private Button connectBtn;
	@FXML
	private Button exitBtn;
	@FXML
	private TextField ipField;
	@FXML
	private TextField portField;
	@FXML
	private TextField dbNameField;
	@FXML
	private TextField dbUsernameField;
	@FXML
	private TextField dbPasswordField;
	@FXML
	private Label lblServerStatus;
	@FXML
	private ListView lvConnectedClients;
	@FXML
	
	private ObservableList<String> connectedClientsList = FXCollections.observableArrayList();

	// empty constructor
	public ServerPortController() {
	}

    @FXML
    void initialize() {
        lvConnectedClients.setItems(connectedClientsList); 
    }
    
	@FXML
	// Method that calls startServer(EchoServer) with the given data entered in the
	// fields
	private void onConnectServerClicked(ActionEvent event) {
		DBConnectionDetails database = new DBConnectionDetails();
		Integer portNumber;
		boolean serverStatus;
		String ipv4 = "";

		try {
			portNumber = Integer.parseInt(portField.getText());
		} catch (Exception ex) {
			System.out.println("Port must be a number");
			return;
		}
		try {
			database.setIp(ipField.getText());
			database.setName(dbNameField.getText());
			database.setUsername(dbUsernameField.getText());
			database.setPassword(dbPasswordField.getText());

			// Check if any field is empty
			if (database.getName().isEmpty() || database.getUsername().isEmpty() || database.getPassword().isEmpty()) {
				throw new IllegalArgumentException("All fields must be filled");
			}

		} catch (IllegalArgumentException e) {
			System.err.println("Error: " + e.getMessage());
			return;
		} catch (Exception e) {
			System.err.println("An unexpected error occurred: " + e.getMessage());
			return;
		}

		// Start the server
		serverStatus = EchoServer.startServer(database, portNumber, this);
		
		try {
			ipv4 = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		
		if (serverStatus) {
			updateServerStatus("Server successfully started.\n ip to connect is: " + ipv4 + "\n " + "on Port: " + portNumber);
			connectBtn.setDisable(true);
		} else {
			updateServerStatus("Failed to start server.");
		}
	}
	
	//add connected client to the list
    public void addConnectedClient(String clientInfo) {
        Platform.runLater(() -> connectedClientsList.add(clientInfo));
    }

    //remove disconnected client from list
    public void removeConnectedClient(String clientInfo) {
        Platform.runLater(() -> connectedClientsList.remove(clientInfo));
    }

	// show server status on the status label
	private void updateServerStatus(String message) {
		Platform.runLater(() -> lblServerStatus.setText(message));
	}

	@FXML
	// Method to exit the program
	private void getExitBtn(ActionEvent event) throws Exception {
		EchoServer.stopServer();
		Platform.exit();
		System.exit(0);
	}

	// Method to start/show the Server Porn GUI
	public void start(Stage primaryStage) throws Exception {
		Pane root = FXMLLoader.load(getClass().getResource("/gui/ServerPort.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setTitle("ServerPort");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}
