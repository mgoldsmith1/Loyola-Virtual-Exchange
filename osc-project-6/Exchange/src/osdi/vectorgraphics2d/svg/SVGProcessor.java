/*
 * VectorGraphics2D: Vector export for Java(R) Graphics2D
 *
 * (C) Copyright 2010-2018 Erich Seifert <dev[at]erichseifert.de>,
 * Michael Seifert <mseifert[at]error-reports.org>
 *
 * This file is part of VectorGraphics2D.
 *
 * VectorGraphics2D is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * VectorGraphics2D is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with VectorGraphics2D.  If not, see <http://www.gnu.org/licenses/>.
 */
package osdi.vectorgraphics2d.svg;

import osdi.vectorgraphics2d.Document;
import osdi.vectorgraphics2d.Processor;
import osdi.vectorgraphics2d.intermediate.CommandSequence;
import osdi.vectorgraphics2d.intermediate.filters.FillPaintedShapeAsImageFilter;
import osdi.vectorgraphics2d.intermediate.filters.StateChangeGroupingFilter;
import osdi.vectorgraphics2d.util.PageSize;

/**
 * {@code Processor} implementation that translates {@link CommandSequence}s to
 * a {@code Document} in the <i>Scaled Vector Graphics</i> (SVG) format.
 */
public class SVGProcessor implements Processor {
	/**
	 * Initializes an {@code SVGProcessor}.
	 */
	public SVGProcessor() {}

	@Override
	public Document getDocument(CommandSequence commands, PageSize pageSize) {
		FillPaintedShapeAsImageFilter shapesAsImages = new FillPaintedShapeAsImageFilter(commands);
		CommandSequence filtered = new StateChangeGroupingFilter(shapesAsImages);
		return new SVGDocument(filtered, pageSize);
	}
}
