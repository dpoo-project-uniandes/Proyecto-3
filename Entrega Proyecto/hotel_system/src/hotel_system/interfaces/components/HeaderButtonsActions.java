package hotel_system.interfaces.components;

import java.awt.event.ActionListener;
import java.util.function.Function;

import hotel_system.interfaces.MainHeader;

public class HeaderButtonsActions {
	
	
	private Function<MainHeader, ActionListener> iconUserAction;
	private Function<MainHeader, ActionListener> backAction;
	private Function<MainHeader, ActionListener> homeAction;
	
	public HeaderButtonsActions(Function<MainHeader, ActionListener> iconUserAction,
			Function<MainHeader, ActionListener> backAction, Function<MainHeader, ActionListener> homeAction) {
		super();
		this.iconUserAction = iconUserAction;
		this.backAction = backAction;
		this.homeAction = homeAction;
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
}
