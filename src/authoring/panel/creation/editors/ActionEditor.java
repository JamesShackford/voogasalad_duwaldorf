package authoring.panel.creation.editors;

import java.util.List;

import authoring.Workspace;
import authoring.panel.creation.pickers.ActionPicker;
import engine.Action;
import engine.Parameter;
import engine.game.EngineController;

/**
 * @author Elliott Bolzan
 *
 */
public class ActionEditor extends Editor {

	private ActionPicker picker;
	private EngineController engine = new EngineController();
	private Action action;

	public ActionEditor(Workspace workspace, ActionPicker picker, Action action, List<String> elements) {
		super(workspace, elements, action, true);
		this.picker = picker;
		this.action = action;
	}

	@Override
	public void selected(String string) {
		action = engine.createAction(string);
		action.setEntity(picker.getEntityMaker().getEntity());
		update(action);
	}

	@Override
	public void save(List<Parameter> data) {
		action.setParams(data);
		picker.add(action);
	}

}
