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

/**
 * A generic LTI System described by a transfer function.
 * 
 * @author Jose Luis Martin - (jlm@joseluismartin.info)
 */
public class GenericLtiSystem implements LtiSystem {
	
	private String transferFunction = "1/(1 + s)";
	private String name = "GeneriLTISystem";

	public String getTransferFunction() {
		return transferFunction.trim().replace("\n", "");
	}

	public void setTransferFunction(String transferFunction) {
		this.transferFunction = transferFunction;
	}

	@Override
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
}
