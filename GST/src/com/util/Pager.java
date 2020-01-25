/*
 * Paginador.java
 *
 * Created on 30 de enero de 2003, 19:37
 */

package com.util;

import java.util.List;

/**
 * Clase que pagina los elemento de una lista
 * 
 * Esta clase contiene los metodos necesarios para paginar una lista.
 * 
 * Las paginas, y los elementos se numeraran a partir del 1.
 * 
 * @author Manuel Guerrero del Pino
 * @version 1.0
 * 
 */

public class Pager implements java.io.Serializable {
	// Datos de a paginar
	private List _objList = null;

	// Número de elementos que se muestran por página
	private int _iElementsPerPage = -1;

	// Página actual que se está mostrando
	private int _iNumberCurrentPage = -1;

	public Pager(List objLista) {
		setList(objLista);
		setElementsPerPage(10);
		setNumberCurrentPage(1);
	}

	public Pager(List objLista, int iElementosPorPagina) {
		setList(objLista);
		// iElementosPorPagina=8;
		setElementsPerPage(iElementosPorPagina);
		setNumberCurrentPage(1);
	}

	// Obtiene la lista que contiene los datos
	public List getList() {
		return _objList;
	}

	// Establece la lista que contiene los datos
	public void setList(List objLista) {
		_objList = objLista;
	}

	// Obtiene el número de elementos que se muestran por cada página
	public int getElementsPerPage() {
		return _iElementsPerPage;
	}

	// Establece el número de elementos que se muestran por cada página
	public void setElementsPerPage(int iElementosPorPagina) {
		if (iElementosPorPagina < 1)
			iElementosPorPagina = 1;
		_iElementsPerPage = iElementosPorPagina;
	}

	// Obtiene el indice de la pagina actual
	public int getNumberCurrentPage() {
		return _iNumberCurrentPage;
	}

	// Establece la pagina actual dentro del rango de paginas
	public void setNumberCurrentPage(int iNumeroPaginaActual) {
		_iNumberCurrentPage = getNumberRealPage(iNumeroPaginaActual);
	}

	// Obtiene la longitud de la lista
	public int getListLength() {
		return ((_objList == null) ? 0 : _objList.size());
	}

	public boolean isEmpty() {
		if (getListLength() == 0) {
			return true;
		}

		return false;
	}

	// Obtiene el número de páginas en que se han dividido los resultados
	public int getNumberPages() {
		int iPaginas = (int) Math.ceil(((double) getListLength())
				/ _iElementsPerPage);
		return iPaginas;
	}

	// Indica si hay páginas anteriores a la que se está mostrando actualmente
	public boolean isPreviousPage() {
		return (_iNumberCurrentPage > 1);
	}

	// Indica si hay páginas posteriores a la que se está mostrando actualmente
	public boolean isNextPage() {
		int iNumeroPaginasTotal = getNumberPages();
		return ((_iNumberCurrentPage < iNumeroPaginasTotal) && (iNumeroPaginasTotal > 0));
	}

	// Obtiene los elementos de la pagina actual
	public List getPage() {
		return getPage(_iNumberCurrentPage);
	}

	// Obtiene el elmenento iElemento de la pagina actual
	public Object getElementPage(int iElemento) {
		return getElementPage(_iNumberCurrentPage, iElemento);
	}

	// Obtiene el indice del primer elemento la pagina actual
	public int getFirstIndexElementPage() {
		return getFirstIndexElementPage(_iNumberCurrentPage);
	}

	// Obtiene el indice del ultimo elemento de la pagina actual
	public int getLastIndexElementPage() {
		return getLastIndexElementPage(_iNumberCurrentPage);
	}

	// Obtiene el indice del ultimo elemento de la pagina actual/
	public int getNumberElementsPerPage() {
		return getNumberElementsPerPage(_iNumberCurrentPage);
	}

	// Se desplaza a la pagina anterior
	public void goPreviousPage() {
		setNumberCurrentPage(getNumberCurrentPage() - 1);
	}

	// Se desplaza a la pagina siguiente
	public void goNextPage() {
		setNumberCurrentPage(getNumberCurrentPage() + 1);
	}

	// Se desplaza a la pagina inicial
	public void goInitpage() {
		setNumberCurrentPage(1);
	}

	// Se desplaza a la pagina final
	public void goLastPage() {
		setNumberCurrentPage(getNumberPages());
	}

	private List getPage(int iNumeroPagina) {
		int iNumeroPaginaReal = getNumberRealPage(iNumeroPagina);
		if (iNumeroPaginaReal == 0)
			return null;
		return _objList.subList(getFirstIndexElementPage(iNumeroPaginaReal),
				getLastIndexElementPage(iNumeroPaginaReal));
	}

	// obtiene el elemento de indice iElemento de la pagina de indice
	// iNumeroPagina.
	private Object getElementPage(int iNumeroPagina, int iElemento) {
		int iNumeroPaginaReal = getNumberRealPage(iNumeroPagina);
		iElemento--;
		if ((iElemento < 0) || (iElemento >= getNumberElementsPerPage()))
			return null;
		iElemento += getFirstIndexElementPage(iNumeroPaginaReal) - 1;

		return _objList.get(iElemento);
	}

	// Obtiene el numero de pagina real (para evitar overflow)
	private int getNumberRealPage(int iNumeroPagina) {
		int iNumeroPaginaReal;
		int iNumeroPaginasTotal = getNumberPages();
		if (iNumeroPagina < 1)
			iNumeroPagina = 1;
		if (iNumeroPagina > iNumeroPaginasTotal)
			iNumeroPaginaReal = iNumeroPaginasTotal;
		else
			iNumeroPaginaReal = iNumeroPagina;

		return iNumeroPaginaReal;
	}

	// Obtiene el indice del primer elemento de una pagina
	private int getFirstIndexElementPage(int iNumeroPagina) {
		int iNumeroPaginaReal = getNumberRealPage(iNumeroPagina);
		if (iNumeroPaginaReal == 0)
			return 0;
		// int iIndice = (iNumeroPaginaReal -1)* _iElementsPerPage + 1;
		int iIndice = (iNumeroPaginaReal - 1) * _iElementsPerPage;
		return iIndice;
	}

	// Obtiene el indice del ultimo elemento de una pagina
	private int getLastIndexElementPage(int iNumeroPagina) {
		int iNumeroPaginaReal = getNumberRealPage(iNumeroPagina);
		if (iNumeroPaginaReal == 0)
			return 0;
		int iLongitudTotal = getListLength();
		int iIndice = ((iNumeroPaginaReal) * _iElementsPerPage);
		if (iIndice > iLongitudTotal)
			iIndice = iLongitudTotal;

		return iIndice;
	}

	// Obtiene el indice del ultimo elemento de la pagina actual
	private int getNumberElementsPerPage(int iNumeroPagina) {
		int iNumeroPaginaReal = getNumberRealPage(iNumeroPagina);
		if (iNumeroPaginaReal == 0)
			return 0;
		return (1 + getLastIndexElementPage(iNumeroPagina) - getFirstIndexElementPage(iNumeroPagina));
	}

}
