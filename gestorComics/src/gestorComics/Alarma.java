package gestorComics;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Alarma extends Thread{

	int identificador;
	
	Comic comic;
	Vineta vineta;
	MedioComunicacion medio;
	int ano;
	int mes;
	int dia;
	int hora;
	int minuto;
	
	public Alarma(int i, Comic c, Vineta v, MedioComunicacion mc, int a, int m, int d, int h, int min) {
		
		identificador = i;
		
		comic = c;
		vineta  = v;
		medio = mc;
		ano = a;
		mes = m;
		dia = d;
		hora = h;
		minuto = min;
	}
	
	public Alarma(Comic c, Vineta v, MedioComunicacion mc, int a, int m, int d, int h, int min) {
		
		identificador = 0;
		
		comic = c;
		vineta  = v;
		medio = mc;
		ano = a;
		mes = m;
		dia = d;
		hora = h;
		minuto = min;
	}
	
	
	@Override
	public String toString() {
		String res = "";
		
		res = "Tiene que presentar la viñeta '" + vineta.getNombre() 
		+ "' perteneciente al comic '" + comic.getNombre() 
		+ "' para el medio de difusion '" + medio.getNombre() + "'.";
		
		return res;
	}
	
	@Override
	public void run() {
		while(!listo()) {
			Thread.yield();
			/*LocalDateTime now = LocalDateTime.now();
			System.out.println(now.getYear());*/
		}
		
		if(listo()) {
			Activar();
		}
	}
	
	private boolean listo() {
		boolean res = false;
		LocalDateTime fecha = LocalDateTime.now();
		//System.out.println(fecha.getYear());
		//System.out.println(ano);
		
		System.out.println("ANo: "+ano);
		System.out.println("Mes: "+mes);
		System.out.println("Dia: "+dia);
		
		
		LocalDateTime fechaInsertada =  LocalDateTime.of(ano,mes,dia, hora, minuto );
		System.out.println(fechaInsertada);
		
		/*
		if(ano < fecha.getYear()) {
			
			res = true;
			System.out.println("Año: " + fecha.getYear());
			
		}else if (ano == fecha.getYear()) {
			
			if(mes < fecha.getMonthValue()) {
				res = true;
				System.out.println(mes);
				System.out.println("Mes: " + fecha.getMonthValue());
			}else if(mes == fecha.getMonthValue()) {
				
				if(dia < fecha.getDayOfMonth()) {
					res = true;
					System.out.println("Dia: " + fecha.getDayOfMonth());
				}else if(dia == fecha.getDayOfMonth()) {
					
					if(hora < fecha.getHour()) {
						res = true;
						System.out.println(fecha.getHour());
					}else if(hora == fecha.getHour()) {
						
						if(minuto <= fecha.getMinute()) {
							res = true;
							System.out.println(fecha.getMinute());
						}
					}
					
				}
			}
		}*/
		
		return fechaInsertada.isBefore(LocalDateTime.now());
	}
	
	private void Activar() {
		
		
		ManagerAlarmas.instance().activarAlarma(this);
	}
	
	public int getIdentificador() {
		return identificador;
	}
	
	public void setIdentificador(int id) {
		identificador = id;
	}
	
	public Comic getComic() {
		return comic;
	}
	
	public Vineta getVineta() {
		return vineta;
	}
	
	public MedioComunicacion getMedioComunicacion() {
		return medio;
	}
	
	public int getAno() {
		return ano;
	}
	
	public int getMes() {
		return mes;
	}
	
	public int getDia() {
		return dia;
	}
	
	public int getHora() {
		return hora;
	}
	
	public int getMinuto() {
		return minuto;
	}
	
}