/**
    DataDude is a data managing applicationdesigned to have mny types of data in one application
    Copyright (C) 2015  Ahmed R. (theTechnoKid)

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.datadude.nodes;

import java.awt.*;
import java.util.*;

import javax.swing.*;

public abstract class Node extends JInternalFrame {
	private static final long serialVersionUID = -2795471245561416657L;
	ArrayList<Component> comps = new ArrayList<Component>();	
	protected String title;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		super.setTitle(title);
		this.title = title;
	}
}
