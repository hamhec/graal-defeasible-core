/*
 * Copyright (C) Inria Sophia Antipolis - Méditerranée / LIRMM
 * (Université de Montpellier & CNRS) (2014 - 2017)
 *
 * Contributors :
 *
 * Clément SIPIETER <clement.sipieter@inria.fr>
 * Mélanie KÖNIG
 * Swan ROCHER
 * Jean-François BAGET
 * Michel LECLÈRE
 * Marie-Laure MUGNIER <mugnier@lirmm.fr>
 *
 *
 * This file is part of Graal <https://graphik-team.github.io/graal/>.
 *
 * This software is governed by the CeCILL  license under French law and
 * abiding by the rules of distribution of free software.  You can  use,
 * modify and/ or redistribute the software under the terms of the CeCILL
 * license as circulated by CEA, CNRS and INRIA at the following URL
 * "http://www.cecill.info".
 *
 * As a counterpart to the access to the source code and  rights to copy,
 * modify and redistribute granted by the license, users are provided only
 * with a limited warranty  and the software's author,  the holder of the
 * economic rights,  and the successive licensors  have only  limited
 * liability.
 *
 * In this respect, the user's attention is drawn to the risks associated
 * with loading,  using,  modifying and/or developing or reproducing the
 * software by the user in light of its specific status of free software,
 * that may mean  that it is complicated to manipulate,  and  that  also
 * therefore means  that it is reserved for developers  and  experienced
 * professionals having in-depth computer knowledge. Users are therefore
 * encouraged to load and test the software's suitability as regards their
 * requirements in conditions enabling the security of their systems and/or
 * data to be ensured and,  more generally, to use and operate it in the
 * same conditions as regards security.
 *
 * The fact that you are presently reading this means that you have had
 * knowledge of the CeCILL license and that you accept its terms.
 */
package fr.lirmm.graphik.graal.defeasible.core.io;

import java.io.Reader;

import fr.lirmm.graphik.graal.defeasible.core.io.parser.DLGP2Parser;
import fr.lirmm.graphik.graal.defeasible.core.io.parser.ParseException;
import fr.lirmm.graphik.util.stream.InMemoryStream;

class DefeasibleProducer implements Runnable {

	private Reader reader;
	private InMemoryStream<Object> buffer;

	DefeasibleProducer(Reader reader, InMemoryStream<Object> buffer) {
		this.reader = reader;
		this.buffer = buffer;
	}

	public void run() {
		DLGP2Parser parser = new DLGP2Parser(new InternalTermFactory(), reader);
		parser.addParserListener(new DlgpDefeasibleListener(buffer));
		parser.setDefaultBase("");

		try {
			parser.document();
		} catch (ParseException e) {
			buffer.write(new DlgpParseException(e));
		} catch (Throwable e) {
			buffer.write(new DlgpParseException(e.getMessage(), parser.token.beginLine, parser.token.beginColumn));
		} finally {
			buffer.close();
		}
	}
}