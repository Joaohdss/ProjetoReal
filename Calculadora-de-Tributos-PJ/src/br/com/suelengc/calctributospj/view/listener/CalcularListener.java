package br.com.suelengc.calctributospj.view.listener;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import br.com.suelengc.calctributospj.R;
import br.com.suelengc.calctributospj.controller.TributoFactory;
import br.com.suelengc.calctributospj.domain.TipoBaseCalculo;
import br.com.suelengc.calctributospj.domain.TipoTributacao;
import br.com.suelengc.calctributospj.model.LucroPresumido;
import br.com.suelengc.calctributospj.model.NotaFiscal;
import br.com.suelengc.calctributospj.model.SimplesNacional;
import br.com.suelengc.calctributospj.model.Tributo;
import br.com.suelengc.calctributospj.preference.PreferenciasCalculo;
import br.com.suelengc.calctributospj.view.controller.Formatter;
import br.com.suelengc.calctributospj.view.controller.Validator;

public class CalcularListener implements OnClickListener, BaseListener {
	private PreferenciasCalculo preferencias;
	private TipoBaseCalculo baseCalculo;
	private NotaFiscal notaFiscal;
	private Tributo tributo;
	private View p;
	private CalculatorCallback callback;
	
	private TextView tvvalor_bruto, tvvalor_liquido, tvirpj_retido, tvcofins_retido, tvpis_retido, tvcsll_retido, tvinss_darf, tvirpj_darf, tvcsll_darf, tvtotaldescontosmensais;
	private TextView tvtributo_unificado;
	private EditText edValorBruto, edValorHora, edQtdeHora;
	
	public interface CalculatorCallback {
		public void onFinishCalculator(boolean sucess, NotaFiscal notaFiscal);
	}
	
	public CalcularListener(PreferenciasCalculo preferencias, TipoBaseCalculo baseCalculo, CalculatorCallback callback) {
		this.preferencias = preferencias;
		this.baseCalculo = baseCalculo;
		this.callback = callback;
	}

	@Override
	public void onClick(View view) {
		p = (View) view.getRootView();
		Context context = p.getContext();
		
		if (p != null) {
			
			if (baseCalculo.equals(TipoBaseCalculo.VALOR_BRUTO)) {
				edValorBruto = (EditText) p.findViewById(R.id_calc.valorBruto);

				if (!validaDadosEntrada()) {
					return;
				}
				
				double valorBruto = Double.parseDouble(edValorBruto.getText().toString());

				notaFiscal = new NotaFiscal(valorBruto);
				
			} else if (baseCalculo.equals(TipoBaseCalculo.VALOR_HORA)) {
				edValorHora = (EditText) p.findViewById(R.id_calc.valorHora);
				edQtdeHora = (EditText) p.findViewById(R.id_calc.qtdeHoras);
				
				if (!validaDadosEntrada()) {
					return;
				}
				
				double valorHora = Double.parseDouble(edValorHora.getText().toString());
				double qtdeHora = Double.parseDouble(edQtdeHora.getText().toString()); 
				
				notaFiscal = new NotaFiscal(valorHora, qtdeHora);
			}
			
			TributoFactory factory = new TributoFactory();
			tributo = factory.Create(preferencias);
			notaFiscal.setTributos(tributo);
			notaFiscal.CalcularTributos();
			
			setDadosSaida();
			
			callback.onFinishCalculator(true, notaFiscal);
			
			//Ocultar o teclado virtual
            InputMethodManager imm = (InputMethodManager) context.getSystemService(context.INPUT_METHOD_SERVICE);  
            imm.hideSoftInputFromWindow(tvvalor_liquido.getWindowToken(), 0); 
		}
	}

	private boolean validaDadosEntrada() {
		Validator validator = new Validator();
		
		if (baseCalculo.equals(TipoBaseCalculo.VALOR_BRUTO)) {
			if (!validator.validateEditText(edValorBruto)) { 
				return false;
			}			
		} else if (baseCalculo.equals(TipoBaseCalculo.VALOR_HORA)) {
			if (!validator.validateEditText(edValorHora)) { 
				return false;
			}
			
			if (!validator.validateEditText(edQtdeHora)) { 
				return false;
			}	
		}
		return true;
	}

	@Override
	public void getDadosEntrada() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setDadosSaida() {
        
		if (preferencias.getTipoTributacao().equals(TipoTributacao.LUCRO_PRESUMIDO)) {
		
			tvvalor_liquido = (TextView) p.findViewById(R.id_calc.valorliquido);
		    tvvalor_bruto = (TextView) p.findViewById(R.id_calc.valorbruto);
		    tvirpj_retido = (TextView) p.findViewById(R.id_calc.irpj_retido);
		    tvcofins_retido = (TextView) p.findViewById(R.id_calc.cofins_retido);
		    tvpis_retido = (TextView) p.findViewById(R.id_calc.pis_retido);
		    tvcsll_retido = (TextView) p.findViewById(R.id_calc.csll_retido);
		    tvinss_darf = (TextView) p.findViewById(R.id_calc.iss_darf);
		    tvirpj_darf = (TextView) p.findViewById(R.id_calc.irpj_darf);
		    tvcsll_darf = (TextView) p.findViewById(R.id_calc.csll_darf);
		    tvtotaldescontosmensais = (TextView) p.findViewById(R.id_calc.totaldescontosmensais);
	
http://marketplace.eclipse.org/marketplace-client-intro?mpc_install=1225			tvirpj_retido.setText("R$ " + Formatter.DoubleToString(((LucroPresumido)tributo).getIrpjMensal()));
			tvcofins_retido.setText("R$ " + Formatter.DoubleToString(((LucroPresumido)tributo).getCofinsMensal()));
			tvpis_retido.setText("R$ " + Formatter.DoubleToString(((LucroPresumido)tributo).getPisMensal()));
			tvcsll_retido.setText("R$ " + Formatter.DoubleToString(((LucroPresumido)tributo).getCsllMensal()));
	
			tvinss_darf.setText("R$ " + Formatter.DoubleToString(((LucroPresumido)tributo).getIssMensal()));
			tvirpj_darf.setText("R$ " + Formatter.DoubleToString(((LucroPresumido)tributo).getIrpjTrimestral()));
			tvcsll_darf.setText("R$ " + Formatter.DoubleToString(((LucroPresumido)tributo).getCsllTrimetral()));
	
			tvvalor_bruto.setText("R$ " + Formatter.DoubleToString(notaFiscal.getValorBruto()));
			tvtotaldescontosmensais.setText("R$ " + Formatter.DoubleToString(((LucroPresumido)tributo).getValorTotalTributos()));
			tvvalor_liquido.setText("R$ " + Formatter.DoubleToString(notaFiscal.getValorLiquido()));
	
		} else if (preferencias.getTipoTributacao().equals(TipoTributacao.SIMPLES_NACIONAL)) {
			
			tvvalor_liquido = (TextView) p.findViewById(R.id_calc.valorliquido);
		    tvvalor_bruto = (TextView) p.findViewById(R.id_calc.valorbruto);
		    tvtotaldescontosmensais = (TextView) p.findViewById(R.id_calc.totaldescontosmensais);
	
			tvtributo_unificado = (TextView) p.findViewById(R.id_calc.tributo_unificado);
			tvtributo_unificado.setText("R$ " + Formatter.DoubleToString(((SimplesNacional) tributo).getValorTotalTributos()));
	
			tvvalor_bruto.setText("R$ " + Formatter.DoubleToString(notaFiscal.getValorBruto()));
			tvtotaldescontosmensais.setText("R$ " + Formatter.DoubleToString(((SimplesNacional) tributo).getValorTotalTributos()));
			tvvalor_liquido.setText("R$ " + Formatter.DoubleToString(notaFiscal.getValorLiquido()));
			
		}
	}
}
