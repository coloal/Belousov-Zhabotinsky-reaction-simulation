import java.lang.Math.*;
import java.math.BigInteger;
import java.math.BigDecimal;
import java.lang.String.*;
import java.util.Scanner;


class randomGenerator{

	final BigInteger a,dosE31menos1,q,r;
	double dosE31menos1Double = 2147483647;

	randomGenerator()
	{

		//ejemplo26_3
		a = BigDecimal.valueOf(Math.pow(7,5)).toBigInteger();
		dosE31menos1 = BigDecimal.valueOf((Math.pow(2,31))-1).toBigInteger();
		q = dosE31menos1.divide(a);
		r = dosE31menos1.mod(a);
		//fin ejemplo26_3
	}

	int random(int semilla,int nNum)
	{
		if(nNum == 0) return 0;

		return random(((5*semilla)+1)%16,nNum-1);
	}

	int ejemplo26_1a(int semilla)
	{	
		return (5*semilla)%(int)Math.pow(2,5);
	}

	int ejemplo26_1b(int semilla)
	{
		return (7*semilla)%(int)Math.pow(2,5);
	}


	int ejemplo26_2(int semilla)
	{
		return (3*semilla)%31;
	}

	BigInteger ejemplo26_3(BigInteger semilla,int nNum)
	{
		BigInteger x_div_q = semilla.divide(q);
		BigInteger x_mod_q = semilla.mod(q);
		BigInteger semilla_new = (a.multiply(x_mod_q)).subtract(r.multiply(x_div_q));
		BigDecimal sem_sol,m_sol = new BigDecimal(dosE31menos1); 
		
		for(int i=0 ; i<=nNum-1 ; ++i)
		{
		x_div_q = semilla.divide(q);
		x_mod_q = semilla.mod(q);
		
		semilla_new = (a.multiply(x_mod_q)).subtract(r.multiply(x_div_q));
			
		if(semilla_new.compareTo(BigInteger.ZERO) > 0 )
				semilla = semilla_new;
		else
			semilla = semilla_new.add(dosE31menos1);
		}
		return semilla;
	}

	BigInteger UnEjemplo26_3(BigInteger semilla)
	{
		BigInteger x_div_q = semilla.divide(q),x_mod_q = semilla.mod(q);
		BigInteger semilla_new = (a.multiply(x_mod_q)).subtract(r.multiply(x_div_q));
		BigDecimal sem_sol,m_sol = new BigDecimal(dosE31menos1); 
		
		x_div_q = semilla.divide(q);
		x_mod_q = semilla.mod(q);
		
		semilla_new = (a.multiply(x_mod_q)).subtract(r.multiply(x_div_q));
			
		if(semilla_new.compareTo(BigInteger.ZERO) > 0 )
				semilla = semilla_new;
		else
			semilla = semilla_new.add(dosE31menos1);
		
		sem_sol = new BigDecimal(semilla);
			
		return semilla;
	}

	BigInteger UnEjemplo26_42(BigInteger[] s)
	{
		BigInteger cien57 = new BigInteger("157"),cien46 = new BigInteger("146"), cien42 = new BigInteger("142");
		BigInteger qW = new BigInteger("32363"),qX = new BigInteger("31727"),qY = new BigInteger("31657");
		BigInteger qWmenos1 = new BigInteger("32362");

		s[0] = cien57.multiply(s[0]).mod(qW);
		s[1] = cien46.multiply(s[1]).mod(qX);
		s[2] = cien42.multiply(s[2]).mod(qY);
		s[3] = s[0].subtract(s[1]).add(s[2]).mod(qWmenos1);		

		return s[3];
	}

	BigInteger fishman_moore1(BigInteger semilla)
	{
		BigInteger m = new BigInteger("48271");		
		return m.multiply(semilla).mod(dosE31menos1);
	}

	double fishman_moore1Normalizar(BigInteger seed)
	{
		double seedDouble = seed.doubleValue();
		return seedDouble/dosE31menos1Double;
	}

	BigInteger fishman_moore2(BigInteger semilla)
	{
		BigInteger m = new BigInteger("69621");
		return m.multiply(semilla).mod(dosE31menos1);
	}


	BigInteger randu(BigInteger semilla)
	{
		BigInteger dosE16mas3 = BigDecimal.valueOf((Math.pow(2,16))+3).toBigInteger();		
		return (dosE16mas3.multiply(semilla)).mod(dosE31menos1.add(BigInteger.ONE));
	}

	public static void main(String[] args) {
		BigInteger seed = new BigInteger("1"),dos=new BigInteger("2");

		randomGenerator r = new randomGenerator();
		int k = 0;
		for(int i=0 ; i< 640 ;++i)
		{
			seed = r.fishman_moore1(seed);
			System.out.println(r.fishman_moore1Normalizar(seed));
		}
	}

}