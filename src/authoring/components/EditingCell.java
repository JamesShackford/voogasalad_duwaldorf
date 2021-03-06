package authoring.components;

import authoring.Workspace;
import engine.Parameter;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Alert;
import javafx.scene.control.TableCell;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * @author Elliott Bolzan
 *
 */
public class EditingCell extends TableCell<Parameter, Object> {

	private TextField textField;
	private KeyCodeField keyCodeField;
	private String invalidEdit;
	private ComponentMaker maker;

	public EditingCell(Workspace workspace) {
		maker = new ComponentMaker(workspace.getResources());
		invalidEdit = workspace.getResources().getString("InvalidEdit");
	}

	@Override
	public void startEdit() {
		if (!isEmpty()) {
			super.startEdit();
			if (getItem() == null || getItem() instanceof KeyCode) {
				createKeyCodeField();
				setGraphic(keyCodeField);
				keyCodeField.selectAll();
				keyCodeField.requestFocus();
			} else {
				createTextField();
				setGraphic(textField);
				textField.selectAll();
			}
			setText(null);
		}
	}

	@Override
	public void cancelEdit() {
		super.cancelEdit();
		setText(getItem() == null ? "" : getItem().toString());
		setGraphic(null);
	}

	@Override
	public void updateItem(Object item, boolean empty) {
		super.updateItem(item, empty);
		if (isEditing()) {
			if (getItem() instanceof KeyCode) {
				if (keyCodeField != null) {
					keyCodeField.setText(getKeyCode().toString());
				}
				setGraphic(keyCodeField);
			} else {
				if (textField != null) {
					textField.setText(getString());
				}
				setGraphic(textField);
			}
			setText(null);
		} else {
			if (getItem() instanceof KeyCode)
				setStyle("-fx-font-weight: bold;");
			else
				setStyle("-fx-font-weight: normal;");
			setText(getString());
			setGraphic(null);
		}
	}

	private void createTextField() {
		textField = new TextField(getString());
		textField.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
		textField.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (!newValue) {
					validateInput();
				}
			}
		});
		textField.setOnKeyPressed(e -> keyPressed(e));
	}

	private void createKeyCodeField() {
		keyCodeField = new KeyCodeField();
		keyCodeField.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
		keyCodeField.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (!newValue) {
					commitEdit(keyCodeField.getKeyCode());
				}
			}
		});
	}

	private void keyPressed(KeyEvent e) {
		if (e.getCode().equals(KeyCode.ENTER)) {
			textField.parentProperty().get().requestFocus();
		}
	}

	private String getString() {
		return getItem() == null ? "" : getItem().toString();
	}

	public KeyCode getKeyCode() {
		return getItem() == null ? null : (KeyCode) getItem();
	}

	private void validateInput() {
		Parameter param = (Parameter) getTableRow().getItem();
		String input = textField.getText();
		try {
			if (param.getParameterClass().equals(Integer.class)) {
				commitEdit(Integer.parseInt(input));
			} else if (param.getParameterClass().equals(Double.class)) {
				commitEdit(Double.parseDouble(input));
			}
			commitEdit(input);
		} catch (Exception e) {
			String content = String.format(invalidEdit, param.getParameterClass().getSimpleName());
			Alert alert = maker.makeAlert(AlertType.ERROR, "ErrorTitle", "ErrorHeader", content);
			alert.show();
		}
	}

}
