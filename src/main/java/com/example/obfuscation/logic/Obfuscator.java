package com.example.obfuscation.logic;

import org.springframework.stereotype.Component;

import java.util.*;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

@Component
public class Obfuscator {
    private Set<String> fSet = new HashSet<>();
    private Set<String> vSet = new HashSet<>();
    private final String removeWhitePattern = "(?<=[A-Za-z\\d])[\\s]+(?=[^A-Za-z\\d])|(?<=[^A-Za-z\\d])[\\s]+(?=[^A-Za-z\\d])";
    private final List<String> stringStorage = new LinkedList<>();
    /*
        Saving all obfuscation options as bit mask.
        From the end:
            - remove '\n' '\t' and whitespaces - always true
            - encode function names - true by default
            - encode vars names - true by default
    */
    private Integer options = 7; // by default 0...0111

    public void setEFN(boolean b) {
        options = b ? options | 2 : options & (Integer.MAX_VALUE - 2);
    }

    public void setEVN(boolean b) {
        options = b ? options | 4 : options & (Integer.MAX_VALUE - 4);
    }

    public boolean getEFN() {
        return (options & 2) == 2;
    }

    public boolean getEVN() {
        return (options & 4) == 4;
    }

    public String obfuscate(String target) {
        return beforeEnd(removeSpaces(encodeFunctionsParams(encodeSimpleVarsNames(encodeFunctionNames(prepare(target))))));
    }

    private String beforeEnd(String target) {
        for (int i = 0; i < stringStorage.size(); i++) {
            target = target.replaceAll("(\\*=\\*" + i + "(?=[^\\d]))", stringStorage.get(i));
        }
        return target;
    }

    private String prepare(String target) {
        target = target.trim();
        Matcher m = Pattern.compile("[\"][^\"]*[\"]|['][^']*[']|[`][^`]*[`]").matcher(target);
        if (m.find()) {
            stringStorage.add(m.group());
            target = target.replaceAll(m.group(), "*=*" + (stringStorage.indexOf(m.group()))); //to find [^`'"][*=*][\d]+[^`'"]
            for (MatchResult result : m.results().toList()) {
                String s = result.group();
                stringStorage.add(s);
                target = target.replace(s, "*=*" + (stringStorage.indexOf(s)));
            }
        }
        return target;
    }

    private String removeSpaces(String target) {
        return target.replaceAll(removeWhitePattern, "").replaceAll("(?<!(if[(][^)]{0,100}))[)][\\s]+(?=[A-Za-z\\d])", ");").replaceAll("(?<=[^A-Za-z\\d])[\\s]+(?=[A-Za-z\\d])", "");
    }

    private String encodeFunctionNames(String target) {
        if (!getEFN()) return target;
        Matcher m = Pattern.compile("(?<=function )[a-zA-Z\\d-_]*(?=[(])").matcher(target);
        if (m.find()) {
            for (String funName : Stream.concat(Stream.of(m.group().trim()), m.results().map(x -> x.group().trim())).toList()) {
                if (!fSet.contains(funName)) {
                    String newName = encode(funName);
                    target = target.replaceAll("(?<=[^A-Za-z\\d-_])" + funName + "(?=[^A-Za-z\\d-_])", newName);
                    fSet.add(funName);
                }
            }
        }
        return target;
    }

    private String encodeSimpleVarsNames(String target) {
        if (!getEVN()) return target;
        Matcher m = Pattern.compile("(?<=let[\\s]|const[\\s]|var[\\s])[\\s]*[a-zA-Z\\d,_-]*(?=[=]|[\\s;]*)").matcher(target);
        if (m.find()) {
            List<String> list = Stream.concat(Arrays.stream(m.group().split(",")), m.results().flatMap(x -> Arrays.stream(x.group().trim().split(",")))).filter(x -> x != null && x.length() != 0).toList();
            target = simpleLogic(list, target);
        }
        return target;
    }

    private String encodeFunctionsParams(String target) {
        if (!getEVN()) return target;
        Matcher m = Pattern.compile("(?<=(function)[\\s][a-zA-Z\\d,_-][(]).*(?=[)])").matcher(target);
        if (m.find()) {
            target = simpleLogic(Stream.concat(
                    Arrays.stream(m.group().split(","))
                            .filter(x -> x != null && x.length() != 0)
                            .map(x -> {
                                x = x.trim();
                                if (x.contains("=")) x = x.substring(0, x.indexOf("="));
                                return x;
                            }), m.results()
                            .flatMap(x -> Arrays.stream(x.group().trim().split(",")).filter(y -> y != null && y.length() != 0).map(y -> {
                                y = y.trim();
                                if (y.contains("=")) y = y.substring(0, y.indexOf("="));
                                return y;
                            }))
            ).toList(), target);
        }
        return target;
    }

    private String simpleLogic(List<String> results, String target) {
        for (String varName : results) {
            if (!vSet.contains(varName)) {
                String newName = encode(varName);
                target = target.replaceAll("(?<=[^A-Za-z\\d-_])" + varName + "(?=[^A-Za-z\\d-_])", " " + newName);
                fSet.add(varName);
            }
        }
        return target;
    }

    private String encode(String target) {
        return target + "encoded";
    }
}
