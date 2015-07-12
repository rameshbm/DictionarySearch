package main.java.com.ramesh.dictionarysearch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

import static java.nio.file.Paths.get;

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
public class TransitionTable {

    private final HashMap<String, String> transitions = new HashMap<String, String>();
    private static final String UTF8_BOM = "\uFEFF";
    private Logger log = LoggerFactory.getLogger(TransitionTable.class);

    public void addTransit(String trns, String cords) {
//        log.debug("Begin TransitionTable:addTransit");
        if (transitions.containsKey(trns))
            transitions.put(trns, transitions.get(trns) + ";" + cords);
        else
            transitions.put(trns, cords);

    }

    public boolean isWord(String wrd) {
//        log.debug("Begin TransitionTable:addTransit");
        return isValid(getTrans(wrd));
    }

    /**
     * @param trans 
     * list of intermediate transitions to check 
     * 
     * @return
     * If list og transistion are present in the transitions returns true else false
     */
    private boolean isValid(List<String> trans) {
//        log.debug("Begin TransitionTable:isValid");
        List<String> cords = new ArrayList<String>();
        boolean firsttransision = true;
        String pPos = "";
        for (String tr : trans) {
            if (!transitions.containsKey(tr)) {
                return false;
            } else {
                String strTemp;
                if (firsttransision) {
                    strTemp = transitions.get(tr).substring(0, 6);
                    cords.add(strTemp);
                    pPos = transitions.get(tr).substring(6, 12);
                    cords.add(pPos);
                    firsttransision = false;
                } else {
                    strTemp = transitions.get(tr);
                    boolean trFound = false;
                    for (String st : strTemp.split(";"))
                        if (st.substring(0, 6).equals(pPos) && (!cords.contains(st.substring(6)))) {
                            strTemp = st.substring(6);
                            trFound = true;
                            break;
                        }
//                strTemp = transitions.get(tr).toString().substring(6);
                    if ((!trFound) || cords.contains(strTemp)) return false;
                    cords.add(strTemp);
                    pPos = strTemp;
                }
            }
        }
        return true;
    }

    /**
     * @param dFile
     * Dictonary of words file
     * @return
     * List of word have valid transisition in the matrix
     * @throws IOException
     * If file is not present or readable throws exception
     */
    public List<String> getWords(String dFile) throws IOException {
//        log.debug("Begin TransitionTable:getWords");
        boolean firstline = true;
        List<String> ret = new ArrayList<String>();
        Stream<String> lines = Files.lines(get(dFile), StandardCharsets.UTF_8);
        for (String line : (Iterable<String>) lines::iterator) {
            if (firstline) {
                if (line.startsWith(UTF8_BOM)) {
                    line = line.substring(1);
                    firstline=false;
                }
            }
            if (isWord(line.toUpperCase())) ret.add(line);
        }
        return ret;
    }

    /**
     * @param wrd
     * break the word into transitions
     * @return
     * List of word transition
     */
    public List<String> getTrans(String wrd) {
//        log.debug("Begin TransitionTable:getWords");
        char pChar = 0;
        List<String> ret = new ArrayList<String>();
        for (char c : wrd.toCharArray()) {
            if (pChar == 0) {
                pChar = c;
            } else {
                ret.add(String.valueOf(pChar) + String.valueOf(c));
                pChar = c;
            }
        }
        return ret;
    }
    public HashMap<String, String> getTransitions() {
        return transitions;
    }
}
