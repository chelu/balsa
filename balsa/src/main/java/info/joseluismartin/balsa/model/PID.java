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

import org.springframework.util.PropertyPlaceholderHelper;
import org.springframework.util.PropertyPlaceholderHelper.PlaceholderResolver;

/**
 * Simple PID Controller model
 * 
 * @author Jose Luis Martin.
 * @since 1.0'
 */
public class PID implements LtiSystem {
	
	public enum Type { P, PI, PD, PID };
	
	private double kp = 1;
	private double ti = 0;
	private double td = 0;
	private Type type = Type.PID;
	private String templatePID = "(${kp} * ( 1 + (1 + ${ti}*s)/(${ti}*s) + (1 + ${td}*s)))";
	private String templatePI = "(${kp} * (1 + (1 + ${ti}*s)/(${ti}*s)))";
	private String templatePD = "(${kp} * (1 + (1 + ${td}*s)))";
	private String templateP = "(${kp})";
	
	private String[] templates = {templateP, templatePI, templatePD, templatePID };
	
	
	@Override
	public String getTransferFunction() {
		PropertyPlaceholderHelper helper = new PropertyPlaceholderHelper("${", "}");
		
		return helper.replacePlaceholders(getTemplate(), createResolver());
	}
	
	private String getTemplate() {
		return this.templates[this.type.ordinal()];
	}


	private PlaceholderResolver createResolver() {
		return new PlaceholderResolver() {
			
			@Override
			public String resolvePlaceholder(String placeholderName) {
				if ("kp".equals(placeholderName))
					return String.valueOf(kp);
				else if ("ti".equals(placeholderName))
					return String.valueOf(ti);
				else if ("td".equals(placeholderName))
					return String.valueOf(td);
				
				return null;
			}
		};
	}

	@Override
	public String getName() {
		return "PID Controller";
	}

	public double getKp() {
		return kp;
	}

	public void setKp(double kp) {
		this.kp = kp;
	}

	public double getTi() {
		return ti;
	}

	public void setTi(double ti) {
		this.ti = ti;
	}

	public double getTd() {
		return td;
	}

	public void setTd(double td) {
		this.td = td;
	}
	
	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}
	
	
}
