/* 
 * Copyright (C) 2012 Jose Luis Martin
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 * 
 *  This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package info.joseluismartin.balsa.model;

import java.util.Locale;

/**
 * @author Jose Luis Martin - (jlm@joseluismartin.info)
 *
 */
public class SecondOrderSystem extends GenericLtiSystem {
	
	private double delta = 1d;
	private double k = 1d;
	private double wn = 1d;
	private int precision = 2;
	
	public String getTransferFunction() {
		double wn2 = wn * wn;
		double doubleDeltaWn = 2 * delta * wn; 
		String format = "(%." + precision + "f)";
		
		return String.format(Locale.US, 
				format + " / (s^2 + " + format +  "* s + " + format + ")", k*wn2, doubleDeltaWn, wn2);
	}
	
	
	public double getDelta() {
		return delta;
	}


	public void setDelta(double delta) {
		this.delta = delta;
	}


	public double getK() {
		return k;
	}


	public void setK(double k) {
		this.k = k;
	}


	public double getWn() {
		return wn;
	}


	public void setWn(double wn) {
		this.wn = wn;
	}


	public int getPrecision() {
		return precision;
	}


	public void setPrecision(int precision) {
		this.precision = precision;
	}
	
}
