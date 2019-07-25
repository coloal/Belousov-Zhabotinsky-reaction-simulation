import java.lang.Math.*;
import java.math.BigInteger;
import java.util.concurrent.*;


class belZab{
	double[][][] a,b,c;
	int dimension_,ventana;
	private static int nucleos=Runtime.getRuntime().availableProcessors();
	CyclicBarrier barrera;
	static int p_,q_;
	double alfa_,beta_,gamma_;

	belZab(int d,double alfa,double beta,double gamma)
	{
		alfa_=alfa;
		beta_=beta;
		gamma_=gamma;

		dimension_=d;
		ventana = (int)(dimension_/nucleos);
		
		barrera = new CyclicBarrier(nucleos);

		a = new double[dimension_][dimension_][2];
		b = new double[dimension_][dimension_][2];
		c = new double[dimension_][dimension_][2];

		randomGenerator r = new randomGenerator();
		BigInteger seed = new BigInteger("1");

		for(int x=0 ; x < dimension_ ; ++x)
			for(int y=0 ; y < dimension_ ; ++y)
			{
				seed = r.fishman_moore1(seed);
				a[x][y][0] = r.fishman_moore1Normalizar(seed);
				seed = r.fishman_moore1(seed);
				b[x][y][0] = r.fishman_moore1Normalizar(seed);
				seed = r.fishman_moore1(seed);
				c[x][y][0] = r.fishman_moore1Normalizar(seed);
			}
		q_ = 1;
		p_ = 0;
	}

	void nextGen()
	{
		ThreadPoolExecutor ejecutor = new ThreadPoolExecutor(nucleos,nucleos,0L,TimeUnit.MILLISECONDS
				,new LinkedBlockingQueue<Runnable>());
		int inicio=0,fin=ventana;


		for(int i = 0; i<=nucleos-1 ; ++i)
        {    
        	ejecutor.execute(new belZabTarea(a,b,c,p_,q_,alfa_,beta_,gamma_,dimension_,inicio,fin,barrera));
            inicio += ventana;
            fin += ventana;
        }
		try
		{
          ejecutor.shutdown();
          ejecutor.awaitTermination(1L,TimeUnit.DAYS);
        }catch(InterruptedException e){}
        if(p_ == 0)
          {
            p_=1;
            q_=0;
          }else{
            p_=0;
            q_=1;
          }
	}
}





class belZabTarea implements Runnable{
	public double[][][] a,b,c;
	public int dimension, fin, inicio;
	int p,q;
	CyclicBarrier barrera_;
	double alfa,beta,gamma;

	belZabTarea(double[][][] v1,double[][][] v2,double[][][] v3,int P,int Q,double alf,double bet,double gamm,int dim,int ini,int f, CyclicBarrier bar)
	{
		alfa = alf;
		beta = bet;
		gamma = gamm;

		p = P;
		q = Q;

		dimension = dim;
		inicio = ini;
		fin = f;
		barrera_ = bar;

		a = v1;
		b = v2;
		c = v3;
		
	}

	public void run()
	{
		for(int x = inicio; x < fin; ++x)
		{
			for(int y = 0; y < dimension; ++y)
			{
				double c_a = 0.0, c_b = 0.0, c_c = 0.0;
				for(int i = x - 1; i <= x+1; ++i)
				{
					for(int j = y - 1; j <= y+1; ++j)
					{

						c_a += a[(i+dimension)%dimension][(j+dimension)%dimension][p];
						c_b += b[(i+dimension)%dimension][(j+dimension)%dimension][p];
						c_c += c[(i+dimension)%dimension][(j+dimension)%dimension][p];
					}
				}
				c_a /= 9.0;
				c_b /= 9.0;
				c_c /= 9.0;

				a[x][y][q] = (double)Math.min(1,Math.max(0, c_a + c_a * (alfa*c_b - gamma*c_c)));
				b[x][y][q] = (double)Math.min(1,Math.max(0, c_b + c_b * (beta*c_c - alfa*c_a)));
				c[x][y][q] = (double)Math.min(1,Math.max(0, c_c + c_c * (gamma*c_a - beta*c_b)));
				
			}
		}
		try{
			
			barrera_.await();
		}catch(BrokenBarrierException e){ e.getMessage();}
		 catch(InterruptedException e){ e.getMessage(); }
	}
}
