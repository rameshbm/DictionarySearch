package main.java.com.ramesh.dictionarysearch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.HashMap;

/*
 * CopyRight: © 2017 Permission granted for acoadamic purpuse only, any type of reproduction is prohibited
 *
 * Author: Ramesh Manchiknati
 *
 * Company: Personal Development
 *
 *Created by Ramesh on 7/11/15..
 *
 */

public class BuildTable {
    //    Stream<String> lines;
    private BufferedReader reader;
    private final HashMap exlList = new HashMap();
    private final TransitionTable trtab = new TransitionTable();
    private final String delMtr;
    private  Logger log = LoggerFactory.getLogger(BuildTable.class);

    public BuildTable(String mFile, String delMtr) throws IOException {
        this.delMtr = delMtr;
//        lines = Files.lines(get(mFile), StandardCharsets.UTF_8);
        InputStream in = new FileInputStream(new File(mFile));

        this.reader = new BufferedReader(new InputStreamReader(in));
    }

    /**
     * @param exclusionList
     */
    public void setExclusionList(String exclusionList) {
//        log.debug("Begin TransitionTable:setExclusionList");
        for (String el : exclusionList.split(";")) exlList.put(el, "");
    }

    /**
     * @return
     * returns the atransition table after converting matxi table into transitions delimeted by delimeter
     * @throws Exception
     * Throws exception in case of file access problem
     */
    public TransitionTable getTransitionsTable() throws Exception {
//        log.debug("Begin TransitionTable:getTransitionsTable");
        String line = reader.readLine();
//        String[] sdims = lines.findFirst().get().split(delMtr);
        String[] sdims = line.split(delMtr);
        if (sdims.length < 2) throw (new Exception("Matrix file dimension info wrong"));
        int rows = Integer.parseInt(sdims[0]);
        int cols = Integer.parseInt(sdims[1]);
        char[][] mtr = new char[rows][cols];
        int row = 0;
        String[] strCols;
//        for (String line : (Iterable<String>) lines.skip(1)::iterator) {
        while ((line = reader.readLine()) != null) {
            strCols = line.split(delMtr);
            for (int c = 0; c < strCols.length; c++)
                mtr[row][c] = strCols[c].charAt(0);
            if (mtr[row].length != cols) {
                log.warn("Matrix has different columns " + mtr[row].length + " than specified " + cols + " at row = " + (row));
                cols = (cols < mtr[row].length ? mtr[row].length : cols);
            }
            if (mtr[row].length > 0) row++;
        }
        if (rows != row) log.warn("Matrix has insufficient rows dim syas = " + rows + " but got rows=" + row);
        for (row = 0; row < rows; row++)
            for (int col = 0; col < cols; col++) {
                getTransition(mtr, row, col);
            }
        return trtab;
    }

    /**
     * @param mt
     * matrix chararray
     * @param ridx
     * cell row position to get all sourrnded transisions
     * @param cidx
     * cell col position to get all sourrnded transisions
     */
    private void getTransition(char[][] mt, int ridx, int cidx) {
//        log.debug("Begin TransitionTable:getTransition");
        int rows = mt.length - 1;
        for (int r = ((ridx - 1) < 0 ? ridx : ridx - 1); r <= ((ridx + 1) > rows ? rows : ridx + 1); r++) {
            int cols = mt[r].length - 1;
            for (int c = ((cidx - 1) < 0 ? cidx : cidx - 1); c <= ((cidx + 1) > cols ? cols : cidx + 1); c++)
                if (!(ridx == r && cidx == c)) {
                    String cords = String.format("%03d", ridx) + String.format("%03d", cidx) + String.format("%03d", r) + String.format("%03d", c);
                    trtab.addTransit(String.valueOf(mt[ridx][cidx]) + String.valueOf(mt[r][c]), cords);
                }
        }
    }
}
