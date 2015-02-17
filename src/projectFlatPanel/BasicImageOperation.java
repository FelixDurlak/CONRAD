package projectFlatPanel;

import imagescience.transform.Transform;
import edu.stanford.rsl.conrad.data.numeric.Grid2D;
import edu.stanford.rsl.conrad.data.numeric.InterpolationOperators;

public class BasicImageOperation extends Grid2D{

	public BasicImageOperation(int width, int height) {
		super(width, height);
		

		
		// a, b, midPointx, midPointy, intesity, theta
		this.drawEllipse(this, Math.round((float) 0.45*height), Math.round((float)0.4*width), Math.round((float)height/2), Math.round((float)width/2), 1, 0);
		this.drawEllipse(this, Math.round((float) 0.42*height), Math.round((float)0.37*width), Math.round((float)height/2), Math.round((float)width/2), 0.3, 0);
		this.drawEllipse(this, Math.round((float) 0.15*height), Math.round((float)0.1*width), height/2+0.5, width/2+0.5, 1, 0);
		this.drawEllipse(this, Math.round((float) 0.06*height), Math.round((float)0.03*width), Math.round((float)3*height/5), Math.round((float)5*width/7), 0.9, Math.PI/3);
		this.drawEllipse(this, Math.round((float) 0.2*height), Math.round((float)0.05*width), Math.round((float)3*height/5), Math.round((float)1*width/3), 0.8, -Math.PI/3);
		this.drawEllipse(this, Math.round((float) 0.1*height), Math.round((float)0.09*width), Math.round((float)height/2), Math.round((float)4*width/7), 0.1, Math.PI/8);
		this.drawEllipse(this, Math.round((float) 0.02*height), Math.round((float)0.03*width), Math.round((float)5*height/7), Math.round((float)3*width/7), 0, Math.PI/5);
		this.drawEllipse(this, Math.round((float) 0.03*height), Math.round((float)0.04*width), Math.round((float)4*height/5), Math.round((float)4*width/7), 1, Math.PI/4);
	}

	public static void main(String args[]) {
		BasicImageOperation test = new BasicImageOperation(64, 64);
		test.show();
		BasicImageOperation sinogram = getSinogram(test);
		sinogram.show();
	}
	
	private static BasicImageOperation getSinogram(BasicImageOperation bio) {
		BasicImageOperation sinogram = new BasicImageOperation((int)Math.sqrt(Math.pow(bio.getWidth(),2) + Math.pow(bio.getHeight(),2)),90);
		
		for (int s = -sinogram.getWidth()/2; s < sinogram.getWidth()/2; s++) {
			for (int thetaGrad = 0; thetaGrad < sinogram.getHeight(); thetaGrad++) {
				double theta = thetaGrad*2* Math.PI/180;
				
				
				double sum = 0;
				for (int y = 0; y < bio.getHeight(); y++) {
					for (int x = 0; x < bio.getWidth(); x++) {
						
						if (Math.round((x-bio.getWidth()/2) * Math.cos(theta) + (y-bio.getHeight()/2) * Math.sin(theta)) == s) {
							sum += bio.getAtIndex(x, y);
						}
						
						
					}
				} 
				
				sinogram.putPixelValue(s+sinogram.getWidth()/2, thetaGrad, sum);
// Kommentar

			
			}
			System.out.println(s);
		}
		
		
		return sinogram;
	}

	public void drawEllipse(BasicImageOperation orig, int a, int b, double midPointx, double midPointy, double intensity, double theta) {
		
		for (int row = 0; row < orig.getHeight(); row++) {
			for (int col = 0; col < orig.getWidth(); col++) {

				double xTransRot = Math.cos(theta) * (col-midPointx) - Math.sin(theta) * (row - midPointy);
				double yTransRot = Math.sin(theta) * (col-midPointx) + Math.cos(theta) * (row - midPointy);
				
				if ((Math.pow(xTransRot,2)/(a*a)) + (Math.pow(yTransRot,2)/(b*b)) <= 1.0) {
					orig.putPixelValue(row, col, intensity);
				}
				
				
				
			}
		}
		
		
		return;
	}


}
