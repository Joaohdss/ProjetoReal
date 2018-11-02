package br.com.suelengc.calctributospj.share;

import android.content.Context;
import android.content.Intent;

public class Email {

	private String content;
	
	public Email() {}
	
	public Email(String content) {
		this.content = content;
	}
	
	public void openIntentEmail(Context context) {
		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType("message/rfc822");
		intent.putExtra(Intent.EXTRA_SUBJECT, "C�lculo de Tributos");
		intent.putExtra(Intent.EXTRA_TEXT, content);
		context.startActivity(Intent.createChooser(intent, "Selecione a aplica��o de email:"));
	}
	
	public void setContent(String content) {
		this.content = content;
	}
}
