/* 
 * Copyright (C) 2013 Jose Luis Martin
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
 * 
 * @author Jose Luis Martin - (jlm@joseluismartin.info)
 */
public class FirstOrderSystem extends GenericLtiSystem {

	private double k = 1.0d;
	private double tao = 1.0d;
	private int precision = 2;
	
	public int getPrecision() {
		return precision;
	}

	public void setPrecision(int precision) {
		this.precision = precision;
	}

	@Override
	public String getTransferFunction() {
		double a = 1/tao;
		double b = k*a;
		
		return String.format(Locale.US, "%." + precision + "f/(s+%."+  precision + "f)", b, a); 
	}

	public double getK() {
		return k;
	}

	public void setK(double k) {
		this.k = k;
	}

	public double getTao() {
		return tao;
	}

	public void setTao(double tao) {
		this.tao = tao;
	}
	
}
