package com.rodrigomartinez.clarity.challenge.listhosts;

import com.rodrigomartinez.clarity.listhosts.ListConnectedHosts;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("ListConnectedHostsTest")
public class ListConnectedHostsTest {

    static Set<String> hostsConnectedToReghan;

    @BeforeAll
    public static void generateListsOfHosts() {
        hostsConnectedToReghan = new HashSet<>(Arrays.asList(
                "Blancaestela",
                "Giordano",
                "Janeel",
                "Shaya",
                "Geral",
                "Tyreonna",
                "Darmesha",
                "Shadie",
                "Muaad"
        ));
    }

    @Test
    @DisplayName("ListConnectedHosts using Reghan as host")
    public void testListConnectedHosts() {
        ListConnectedHosts listConnectedHosts = new ListConnectedHosts(
                getClass().getClassLoader().getResource("input-file-10000_4.txt").getPath(),
                "2019-08-12 22:00:00",
                "2019-08-13 22:00:00",
                "Rehgan");
        assertTrue(listConnectedHosts.processLog().containsAll(hostsConnectedToReghan));
    }
}
