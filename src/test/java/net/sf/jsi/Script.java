//   Script.java
//   Java Spatial Index Library
//   Copyright (C) 2002-2005 Infomatiq Limited.
//  
//  This library is free software; you can redistribute it and/or
//  modify it under the terms of the GNU Lesser General Public
//  License as published by the Free Software Foundation; either
//  version 2.1 of the License, or (at your option) any later version.
//  
//  This library is distributed in the hope that it will be useful,
//  but WITHOUT ANY WARRANTY; without even the implied warranty of
//  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
//  Lesser General Public License for more details.
//  
//  You should have received a copy of the GNU Lesser General Public
//  License along with this library; if not, write to the Free Software
//  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307 USA

package net.sf.jsi;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

import junit.framework.TestCase;


/**
 * Script
 */
public class Script
{

	private static final Logger log = Logger.getLogger(Script.class.getName());

	static final int REFERENCE_COMPARISON = 1;
	static final int REFERENCE_GENERATE = 2;

	private float canvasSize = 100000F;


	private static void writeOutput(String outputLine, PrintWriter outputFile, LineNumberReader referenceFile)
	{
		try {
			outputFile.println(outputLine);
			outputFile.flush();
			if ( referenceFile != null ) {
				String referenceLine = referenceFile.readLine();
				if ( !outputLine.equals(referenceLine) ) {
					log.severe("Output does not match reference on line " + referenceFile.getLineNumber());
					log.severe(" Reference result: " + referenceLine);
					log.severe(" Test result:      " + outputLine);
					TestCase.assertTrue("Output does not match reference on line " + referenceFile.getLineNumber(), false);
				}
			}
		}
		catch (IOException e) {
			log.severe("IOException while writing test results");
		}
	}


	private static float quantize(double d, int quantizer)
	{
		if ( quantizer <= 0 ) {
			return (float)d;
		}

		d /= quantizer;
		d = Math.round(d);
		d *= quantizer;

		return (float)d;
	}


	private static Area getRandomRectangle(Random r, float rectangleSize, float canvasSize, int quantizer)
	{
		float x1 = quantize(r.nextGaussian() * canvasSize, quantizer);
		float y1 = quantize(r.nextGaussian() * canvasSize, quantizer);
		float x2 = x1 + quantize(r.nextGaussian() * rectangleSize, quantizer);
		float y2 = y1 + quantize(r.nextGaussian() * rectangleSize, quantizer);

		return new Area(x1, y1, x2, y2);
	}


	/**
	 * @return Time taken to execute method, in milliseconds.
	 */
	public long run(String indexType, Properties indexProperties, String testId, int testType)
	{
		if ( log.isLoggable(Level.INFO) ) {
			log.info("runScript: " + indexType + ", testId=" + testId);
			if ( indexProperties != null ) {
				log.info("minEntries=" + indexProperties.getProperty("MinNodeEntries") +
					", maxEntries=" + indexProperties.getProperty("MaxNodeEntries") +
					", treeVariant=" + indexProperties.getProperty("TreeVariant"));
			}
		}

		RTree si = SpatialIndexFactory.newInstance(indexType, indexProperties);

		ListDecorator ld = new SortedListDecorator(si);

		Random random = new Random();
		DecimalFormat df = new DecimalFormat();
		df.setMinimumFractionDigits(4);
		df.setMaximumFractionDigits(4);
		df.setMinimumIntegerDigits(7);
		df.setMaximumIntegerDigits(7);
		df.setPositivePrefix(" ");
		df.setGroupingUsed(false);

		int quantizer = -1;

		String strTestInputRoot = "/test-inputs" + File.separator + "test-" + testId;

		String strTestResultsRoot = "target/test-results" + File.separator + "test-" + testId;

		// open test input file for read-only
		LineNumberReader inputFile = null;
		String inputFilename = strTestInputRoot + "-in";

		try {
			inputFile = new LineNumberReader(new InputStreamReader(getClass().getResourceAsStream(inputFilename)));
		}
		catch (Throwable t) {
			log.severe("Unable to open test input file " + inputFilename);
			TestCase.assertTrue("Unable to open test input file " + inputFilename, false);
			return -1;
		}

		// open reference results file for read-only. Filename is of form:
		// test-testId-reference
		LineNumberReader referenceFile = null;
		if ( testType == REFERENCE_COMPARISON ) {
			String referenceFilename = strTestResultsRoot + "-reference";
			try {
				referenceFile = new LineNumberReader(new InputStreamReader(new FileInputStream(referenceFilename)));
			}
			catch (FileNotFoundException e) {
				log.severe("Unable to open reference test results file " + referenceFilename);
				TestCase.assertTrue("Unable to open reference test results file " + referenceFilename, false);
				return -1;
			}
		}

		// open actual results file for writing. Filename is of form
		// test-testId-indexType-revision-datetime, unless generating reference results.
		PrintWriter outputFile = null;

		String outputFilename = null;
		if ( testType == REFERENCE_COMPARISON ) {
			outputFilename = strTestResultsRoot + "-rtree" +
				"-" + new SimpleDateFormat("yyMMddHHmmss").format(new Date());
		}
		else {
			outputFilename = strTestResultsRoot + "-reference";
			if ( new File(outputFilename).exists() ) {
				log.info("Reusing existing reference file: " + outputFilename);
				return 0;
			}
		}

		new File(outputFilename).getParentFile().mkdirs();

		try {
			outputFile = new PrintWriter(new FileOutputStream(outputFilename));
		}
		catch (FileNotFoundException e) {
			log.severe("Unable to open test output results file " + outputFilename);
			TestCase.assertTrue("Unable to open test output results file " + outputFilename, false);
			return -1;
		}

		long scriptStartTime = System.currentTimeMillis();

		try {
			// read lines from the test input file
			while ( inputFile.ready() ) {
				String inputLine = inputFile.readLine();

				if ( inputLine.startsWith("#") ) {
					continue;
				}

				StringBuffer outputBuffer = new StringBuffer(inputLine);

				StringTokenizer st = new StringTokenizer(inputLine);
				while ( st.hasMoreTokens() ) {
					String operation = st.nextToken().toUpperCase();
					if ( operation.equals("DISTANCEQUANTIZER") ) {
						quantizer = Integer.parseInt(st.nextToken());
					}
					else if ( operation.equals("RANDOMIZE") ) {
						random.setSeed(Integer.parseInt(st.nextToken()));
						writeOutput(outputBuffer.toString() + " : OK", outputFile, referenceFile);
					}
					else if ( operation.equals("ADDRANDOM") ) {
						int count = Integer.parseInt(st.nextToken());
						int startId = Integer.parseInt(st.nextToken());
						float rectangleSize = Float.parseFloat(st.nextToken());

						writeOutput(outputBuffer.toString(), outputFile, referenceFile);

						long startTime = System.currentTimeMillis();

						for ( int id = startId; id < startId + count; id++ ) {
							Area r = getRandomRectangle(random, rectangleSize, canvasSize, quantizer);
							si.add(r, id);

							String outputLine = "  " + id + " " + r.toString() + " : OK";
							writeOutput(outputLine, outputFile, referenceFile);
						}
						long time = System.currentTimeMillis() - startTime;
						if ( log.isLoggable(Level.FINE) ) {
							log.fine("Added " + count + " entries in " + time + "ms (" + time / (float)count + " ms per add)");
						}
					}
					else if ( operation.equals("DELETERANDOM") ) {
						int count = Integer.parseInt(st.nextToken());
						int startId = Integer.parseInt(st.nextToken());
						float rectangleSize = Float.parseFloat(st.nextToken());
						writeOutput(outputBuffer.toString(), outputFile, referenceFile);

						long startTime = System.currentTimeMillis();

						int successfulDeleteCount = 0;
						for ( int id = startId; id < startId + count; id++ ) {
							Area r = getRandomRectangle(random, rectangleSize, canvasSize, quantizer);
							boolean deleted = si.delete(r, id);

							if ( deleted ) {
								successfulDeleteCount++;
							}

							String outputLine = "  " + id + " " + r.toString() + " : " + deleted;
							writeOutput(outputLine, outputFile, referenceFile);
						}
						long time = System.currentTimeMillis() - startTime;
						if ( log.isLoggable(Level.FINE) ) {
							log.fine("Attempted to delete " + count + " entries (" + successfulDeleteCount + " successful) in " + time
								+ "ms (" + time / (float)count + " ms per delete)");
						}
					}
					else if ( operation.equals("NEARESTRANDOM") ) {
						int queryCount = Integer.parseInt(st.nextToken());

						writeOutput(outputBuffer.toString(), outputFile, referenceFile);

						long startTime = System.currentTimeMillis();
						int totalEntriesReturned = 0;

						for ( int id = 0; id < queryCount; id++ ) {
							float x = (float)random.nextGaussian() * canvasSize;
							float y = (float)random.nextGaussian() * canvasSize;

							List<Integer> l = ld.nearest(new Spot(x, y), Float.POSITIVE_INFINITY);
							totalEntriesReturned += l.size();

							StringBuffer tempBuffer = new StringBuffer("  " + id + " " +
								df.format(x) + " " +
								df.format(y) + " : OK");

							Iterator<Integer> i = l.iterator();
							while ( i.hasNext() ) {
								tempBuffer.append(' ');
								tempBuffer.append(i.next()).toString();
							}
							writeOutput(tempBuffer.toString(), outputFile, referenceFile);
						}
						long time = System.currentTimeMillis() - startTime;
						if ( log.isLoggable(Level.INFO) ) {
							log.info("NearestQueried " + queryCount + " times in " + time + "ms. Per query: " + time / (float)queryCount
								+ " ms, " + (totalEntriesReturned / (float)queryCount) + " entries");
						}
					}
					else if ( operation.equals("NEARESTNRANDOM") ) {
						int queryCount = Integer.parseInt(st.nextToken());
						int n = Integer.parseInt(st.nextToken());

						writeOutput(outputBuffer.toString(), outputFile, referenceFile);

						long startTime = System.currentTimeMillis();
						int totalEntriesReturned = 0;

						for ( int id = 0; id < queryCount; id++ ) {
							float x = (float)random.nextGaussian() * canvasSize;
							float y = (float)random.nextGaussian() * canvasSize;

							List<Integer> l = ld.nearestN(new Spot(x, y), n, Float.POSITIVE_INFINITY);

							totalEntriesReturned += l.size();

							StringBuffer tempBuffer = new StringBuffer("  " + id + " " +
								df.format(x) + " " +
								df.format(y) + " : OK");

							Iterator<Integer> i = l.iterator();
							while ( i.hasNext() ) {
								tempBuffer.append(' ');
								tempBuffer.append(i.next()).toString();
							}

							writeOutput(tempBuffer.toString(), outputFile, referenceFile);
						}
						long time = System.currentTimeMillis() - startTime;
						if ( log.isLoggable(Level.INFO) ) {
							log.info("NearestNQueried " + queryCount + " times in " + time + "ms. Per query: " + time / (float)queryCount
								+ " ms, " + (totalEntriesReturned / (float)queryCount) + " entries");
						}
					}
					else if ( operation.equals("INTERSECTRANDOM") ) {
						int queryCount = Integer.parseInt(st.nextToken());
						float rectangleSize = Float.parseFloat(st.nextToken());

						writeOutput(outputBuffer.toString(), outputFile, referenceFile);

						long startTime = System.currentTimeMillis();
						int totalEntriesReturned = 0;

						for ( int id = 0; id < queryCount; id++ ) {
							Area r = getRandomRectangle(random, rectangleSize, canvasSize, quantizer);
							List<Integer> l = ld.intersects(r);
							totalEntriesReturned += l.size();

							Iterator<Integer> i = l.iterator();
							StringBuffer tempBuffer = new StringBuffer("  " + id + " " + r.toString() + " : OK");

							while ( i.hasNext() ) {
								tempBuffer.append(' ');
								tempBuffer.append(i.next()).toString();
							}
							writeOutput(tempBuffer.toString(), outputFile, referenceFile);
						}
						long time = System.currentTimeMillis() - startTime;
						if ( log.isLoggable(Level.INFO) ) {
							log.info("IntersectQueried " + queryCount + " times in " + time + "ms. Per query: " + time / (float)queryCount
								+ " ms, " + (totalEntriesReturned / (float)queryCount) + " entries");
						}
					}
					else if ( operation.equals("CONTAINSRANDOM") ) {
						int queryCount = Integer.parseInt(st.nextToken());
						float rectangleSize = Float.parseFloat(st.nextToken());

						writeOutput(outputBuffer.toString(), outputFile, referenceFile);

						long startTime = System.currentTimeMillis();
						int totalEntriesReturned = 0;

						for ( int id = 0; id < queryCount; id++ ) {
							Area r = getRandomRectangle(random, rectangleSize, canvasSize, quantizer);
							List<Integer> l = ld.contains(r);
							totalEntriesReturned += l.size();

							Iterator<Integer> i = l.iterator();
							StringBuffer tempBuffer = new StringBuffer("  " + id + " " + r.toString() + " : OK");

							while ( i.hasNext() ) {
								tempBuffer.append(' ');
								tempBuffer.append(i.next()).toString();
							}
							writeOutput(tempBuffer.toString(), outputFile, referenceFile);
						}
						long time = System.currentTimeMillis() - startTime;
						if ( log.isLoggable(Level.INFO) ) {
							log.info("ContainsQueried " + queryCount + " times in " + time + "ms. Per query: " + time / (float)queryCount
								+ " ms, " + (totalEntriesReturned / (float)queryCount) + " entries");
						}
					}
					else if ( operation.equals("ADD") ) {
						int id = Integer.parseInt(st.nextToken());
						float x1 = Float.parseFloat(st.nextToken());
						float y1 = Float.parseFloat(st.nextToken());
						float x2 = Float.parseFloat(st.nextToken());
						float y2 = Float.parseFloat(st.nextToken());

						si.add(new Area(x1, y1, x2, y2), id);

						outputBuffer.append(" : OK");
						writeOutput(outputBuffer.toString(), outputFile, referenceFile);
					}
					else if ( operation.equals("DELETE") ) {
						int id = Integer.parseInt(st.nextToken());
						float x1 = Float.parseFloat(st.nextToken());
						float y1 = Float.parseFloat(st.nextToken());
						float x2 = Float.parseFloat(st.nextToken());
						float y2 = Float.parseFloat(st.nextToken());

						boolean deleted = si.delete(new Area(x1, y1, x2, y2), id);

						if ( deleted ) {
							outputBuffer.append(" : OK");
						}
						else {
							outputBuffer.append(" : Not found");
						}
						writeOutput(outputBuffer.toString(), outputFile, referenceFile);
					}
					else if ( operation.equals("NEAREST") ) {
						float x = Float.parseFloat(st.nextToken());
						float y = Float.parseFloat(st.nextToken());

						List<Integer> l = ld.nearest(new Spot(x, y), Float.POSITIVE_INFINITY);

						outputBuffer.append(" : OK");

						Iterator<Integer> i = l.iterator();
						while ( i.hasNext() ) {
							outputBuffer.append(" ");
							outputBuffer.append(i.next()).toString();
						}
						writeOutput(outputBuffer.toString(), outputFile, referenceFile);
					}
					else if ( operation.equals("INTERSECT") ) {
						float x1 = Float.parseFloat(st.nextToken());
						float y1 = Float.parseFloat(st.nextToken());
						float x2 = Float.parseFloat(st.nextToken());
						float y2 = Float.parseFloat(st.nextToken());

						List<Integer> l = ld.intersects(new Area(x1, y1, x2, y2));

						outputBuffer.append(" : OK");

						Iterator<Integer> i = l.iterator();
						while ( i.hasNext() ) {
							outputBuffer.append(" ");
							outputBuffer.append(i.next()).toString();
						}
						writeOutput(outputBuffer.toString(), outputFile, referenceFile);
					}
				} // for each token on the current input line
			} // for each input line
		}
		catch (IOException e) {
			log.log(Level.SEVERE, "IOException while running test script in SpatialIndexTest", e);
			return -1;
		}
		long scriptEndTime = System.currentTimeMillis();

		// try and clean up the largest objects to prevent garbage collection
		// from slowing down a future run.
		ld = null;
		si = null;
		System.gc();

		return scriptEndTime - scriptStartTime;
	}
}
