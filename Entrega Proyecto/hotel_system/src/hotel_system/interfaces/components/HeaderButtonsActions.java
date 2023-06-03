package hotel_system.interfaces.components;

import java.awt.event.ActionListener;
import java.util.Map;
import java.util.function.Function;

import hotel_system.interfaces.MainHeader;

public class HeaderButtonsActions {
	
	
	private Function<MainHeader, ActionListener> iconUserAction;
	private Function<MainHeader, ActionListener> backAction;
	private Function<MainHeader, ActionListener> homeAction;
	private Function<MainHeader, ActionListener> logoutAction;
	private Boolean showButtons;
	
	public HeaderButtonsActions(Function<MainHeader, ActionListener> iconUserAction,
			Function<MainHeader, ActionListener> backAction, Function<MainHeader, ActionListener> homeAction, Function<MainHeader, ActionListener> logoutAction) {
		super();
		this.iconUserAction = iconUserAction;
		this.backAction = backAction;
		this.homeAction = homeAction;
		this.logoutAction = logoutAction;
		this.showButtons = true;
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
	
	public Function<MainHeader, ActionListener> getLogoutAction() {
		return logoutAction;
	}
	
	public Boolean showIconUserAction() {
		return iconUserAction != null;
	}

	public Boolean showBackAction() {
		return backAction != null;
	}

	public Boolean showHomeAction() {
		return homeAction != null;
	}
	
	public Boolean showLogoutAction() {
		return logoutAction != null;
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
