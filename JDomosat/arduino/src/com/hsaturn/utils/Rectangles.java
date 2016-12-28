/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hsaturn.utils;

import java.awt.Rectangle;
import java.util.ArrayList;

/**
 *
 * @author hsaturn
 */
public class Rectangles {

	transient ArrayList<Rectangle> rects = new ArrayList<>();
	int minWidth = 10;	// Largeur mini pour pouvoir ajouter un rectangle
	int minHeight = 10;	// Hauteur mini pour pouvoir ajouter un rectangle

	/**
	 * Met à jour la liste de rectangles par rapport à splitter. Par exemple si
	 * rects contient uniquement un rectangle la nouvelle liste contiendra les
	 * rectangles A B C D C D v v +---------------------+ A -> | | | +---------+
	 * | | |splitter | | | +---------+ | B -> | | +---------------------+
	 *
	 * @param splitter
	 */
	public void split(Rectangle splitter) {
		Rectangles newRects = new Rectangles();
		for (Rectangle rect : rects) {
			if (splitter.intersects(rect)) {
				// new rect C ?
				if (splitter.x - rect.x > minWidth) {
					newRects.add(new Rectangle(rect.x, rect.y, splitter.x - rect.x, rect.height));
				} else // rect D ?
				{
					newRects.add(new Rectangle(
							splitter.x + splitter.width,
							rect.y,
							rect.x + rect.width - splitter.x - splitter.width,
							rect.height
					));
				}

				// new rect A ?
				if (splitter.y - rect.y > minHeight) {
					newRects.add(new Rectangle(rect.x, rect.y, rect.width, splitter.y - rect.y));
				} else // new rect B ?
				{
					newRects.add(new Rectangle(
							rect.x,
							splitter.y + splitter.height,
							rect.width,
							rect.y + rect.height - splitter.y - splitter.height
					));
				}
			} else {
				newRects.add(rect);
			}
		}
		rects = newRects.rects;
	}

	public void add(Rectangle newrect) {
		if (!rects.contains(newrect) && newrect.width >= minWidth && newrect.height >= minHeight) {
			rects.add(newrect);
		}
	}

	public Rectangle findRoomFor(Rectangle prect) {
		Rectangle candidate_contains = null;	// Candidat contenant rect
		Rectangle candidate_alternative = null;	// Alternative
		double containsSurface = 9.99e99;			// Superficie candidat contains
		double alternativeSurface =0;				// Superficie candidat alternatif

		prect.x = 0;
		prect.y = 0;

		for (Rectangle r : rects) {
			double surface = r.height * r.height;
			if (r.width >= prect.width && r.height >= prect.height) {
				if (surface < containsSurface) {	// Select smallest ...
					containsSurface = surface;
					candidate_contains = r;
				}
			} else if (candidate_contains == null)
			{
				// Prendre le rectangle qui a la plus grande intersection avec prect
				Rectangle r2 = new Rectangle(0,0,r.width, r.height);
				Rectangle ri = r2.intersection(prect);
				surface = ri.width*ri.height;
				if (surface > alternativeSurface) {
					alternativeSurface = surface;
					candidate_alternative = r;
				}
			}
		}
		if (candidate_contains != null) {
			return candidate_contains;
		}
		return candidate_alternative;
	}
}
