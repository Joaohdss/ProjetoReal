package br.com.suelengc.calctributospj.listener;

import org.junit.Test;

import br.com.suelengc.calctributospj.domain.TipoBaseCalculo;
import br.com.suelengc.calctributospj.preference.PreferenciasCalculo;
import br.com.suelengc.calctributospj.view.listener.CalcularListener;
import android.test.AndroidTestCase;

public class CalcularListenerTest extends AndroidTestCase {

	@SuppressWarnings("unused")
	@Test
	public void testCalcularAPartirDoValorHora() {
		CalcularListener listener = new CalcularListener(new PreferenciasCalculo(getContext()), TipoBaseCalculo.VALOR_HORA, null);
		
	} 
	
}
