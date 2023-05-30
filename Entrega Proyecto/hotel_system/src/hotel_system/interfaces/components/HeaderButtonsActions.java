package hotel_system.interfaces.components;

import java.awt.event.ActionListener;
import java.util.function.Function;

import hotel_system.interfaces.MainHeader;

public class HeaderButtonsActions {
	
	
	private Function<MainHeader, ActionListener> iconUserAction;
	private Function<MainHeader, ActionListener> backAction;
	private Function<MainHeader, ActionListener> homeAction;
	private Boolean showButtons;
	
	public HeaderButtonsActions(Function<MainHeader, ActionListener> iconUserAction,
			Function<MainHeader, ActionListener> backAction, Function<MainHeader, ActionListener> homeAction) {
		super();
		this.iconUserAction = iconUserAction;
		this.backAction = backAction;
		this.homeAction = homeAction;
		this.showButtons = true;
	}
	
	public HeaderButtonsActions(Function<MainHeader, ActionListener> iconUserAction,
			Function<MainHeader, ActionListener> backAction, Function<MainHeader, ActionListener> homeAction, Boolean showButtons) {
		super();
		this.iconUserAction = iconUserAction;
		this.backAction = backAction;
		this.homeAction = homeAction;
		this.showButtons = showButtons;
	}

	public Function<MainHeader, ActionListener> getIconUserAction() {
		return iconUserAction;
	}

	public Function<MainHeader, ActionListener> getBackAction() {
		return backAction;
	}

	public Function<MainHeader, ActionListener> getHomeAction() {
		return homeAction;
	}
	
	public HeaderButtonsActions withButtons() {
		this.showButtons = true;
		return this;
	}
	
	public HeaderButtonsActions withoutButtons() {
		this.showButtons = false;
		return this;
	}
	
	public Boolean showButtons() {
		return this.showButtons;
	}
}
