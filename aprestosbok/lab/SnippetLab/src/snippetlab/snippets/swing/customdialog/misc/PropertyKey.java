/*
 * Prop.java Copyright (C) Wincor Nixdorf.
 */
package snippetlab.snippets.swing.customdialog.misc;

public enum PropertyKey
{
	BUTTON_HEIGHT("button.height"),BUTTON_WIDTH("button.width"),
	BUTTON_GAP_WIDTH("button.gap.width"),
BUTTON_FONT_SIZE("button.font.size"),BUTTON_FONT_NAME("button.font.name"),
	BUTTON_FONT_STYLE("button.font.style"),
OK_ICON("ok.icon"), APPLY_ICON("apply.icon"), 
	CANCEL_ICON("cancel.icon"),HELP_ICON("help.icon"),
OK_TEXT("ok.text"), APPLY_TEXT("apply.text"), 
	CANCEL_TEXT("cancel.text"),HELP_TEXT("help.text"),
OK_MNEMONIC("ok.mnemonic"), APPLY_MNEMONIC("apply.mnemonic"), 
	CANCEL_MNEMONIC("cancel.mnemonic"),HELP_MNEMONIC("help.mnemonic"),
OK_ACCELERATOR("ok.accelerator"), APPLY_ACCELERATOR("apply.accelerator"), 
	CANCEL_ACCELERATOR("cancel.accelerator"),HELP_ACCELERATOR("help.accelerator"),
OK_INSET("ok.inset"), APPLY_INSET("apply.inset"), 
	CANCEL_INSET("cancel.inset"),HELP_INSET("help.inset")
;

private static final String PREFIX="customdialog.";
private String key;

private PropertyKey(String key)
{
	this.key = key;
}

public String getKey()
{
	return PREFIX + key;
}
}
