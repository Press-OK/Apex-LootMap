/*
 * Initializes and stores all locations in the game.
 */

package lm.systems;

import java.util.HashSet;

import org.eclipse.swt.graphics.RGB;

import lm.data.Location;

public class LocationManager {
	private HashSet<Location> locations = new HashSet<Location>();
	
	public LocationManager() {
		initLocations();
	}
	
	public HashSet<Location> getLocations() {
		return this.locations;
	}
	
	private void initLocations() {
		// Relay
		locations.add(new Location(383,60,"Relay Island",new RGB(60,201,96)));
		locations.add(new Location(373,75,"Relay West Upper",new RGB(55,201,96)));
		locations.add(new Location(372,85,"Relay West Middle",new RGB(50,201,96)));
		locations.add(new Location(377,97,"Relay West Lower",new RGB(45,201,96)));
		locations.add(new Location(365,112,"Relay South West",new RGB(40,201,96)));
		locations.add(new Location(390,87,"Relay East",new RGB(75,201,96)));
		locations.add(new Location(402,97,"Relay Main",new RGB(80,201,96)));
		// Wetlands
		locations.add(new Location(401,152,"Wetlands Far East",new RGB(75,200,145)));
		locations.add(new Location(389,152,"Wetlands East",new RGB(75,200,150)));
		locations.add(new Location(369,144,"Wetlands Middle",new RGB(85,200,154)));
		locations.add(new Location(354,159,"Wetlands Beacon",new RGB(95,200,154)));
		locations.add(new Location(341,162,"Wetlands Above Beacon",new RGB(95,200,154)));
		locations.add(new Location(339,181,"Wetlands High Ground",new RGB(110,200,154)));
		// Shattered Forest
		locations.add(new Location(344,214,"Shattered Forest Ruins",new RGB(140,232,21)));
		locations.add(new Location(374,239,"Shattered Forest West",new RGB(145,232,21)));
		locations.add(new Location(387,239,"Shattered Forest East",new RGB(150,232,21)));
		// Swamp
		locations.add(new Location(431,219,"Swamp West Main",new RGB(160,232,21)));
		locations.add(new Location(449,214,"Swamp North Main",new RGB(155,232,21)));
		locations.add(new Location(447,256,"Swamp East Main",new RGB(165,232,21)));
		locations.add(new Location(411,269,"Swamp Corner North",new RGB(175,232,21)));
		locations.add(new Location(434,289,"Swamp Corner East",new RGB(170,232,21)));
		locations.add(new Location(417,306,"Swamp Corner South",new RGB(180,232,21)));
		// Hydro Dam
		locations.add(new Location(342,294,"Hydro Main",new RGB(185,232,21)));
		locations.add(new Location(364,281,"Hydro North West",new RGB(190,232,21)));
		locations.add(new Location(387,286,"Hydro North East",new RGB(195,232,21)));
		locations.add(new Location(392,301,"Hydro South East",new RGB(200,232,21)));
		locations.add(new Location(374,306,"Hydro South West",new RGB(205,232,21)));
		// Repulsor
		locations.add(new Location(370,336,"Repulsor Middle",new RGB(220,232,21)));
		locations.add(new Location(385,346,"Repulsor Main North",new RGB(215,232,21)));
		locations.add(new Location(385,358,"Repulsor Main South",new RGB(210,232,21)));
		locations.add(new Location(357,351,"Repulsor Bunkers",new RGB(225,232,21)));
		locations.add(new Location(347,356,"Repulsor Entrance",new RGB(232,232,21)));
		// Repulsor Lagoon
		locations.add(new Location(360,396,"Garage South of Repulsor",new RGB(35,27,135)));
		locations.add(new Location(314,361,"Repulsor Lagoon Control Tower",new RGB(35,27,140)));
		locations.add(new Location(287,319,"Repulsor Lagoon Overhang",new RGB(35,27,125)));
		locations.add(new Location(279,336,"Repulsor Lagoon North",new RGB(35,27,130)));
		locations.add(new Location(277,384,"Repulsor Lagoon West",new RGB(35,27,145)));
		locations.add(new Location(305,401,"Repulsor Lagoon South",new RGB(35,27,150)));
		// Water Treatment
		locations.add(new Location(255,444,"Water Main West",new RGB(35,27,188)));
		locations.add(new Location(262,441,"Water Inner West",new RGB(35,27,180)));
		locations.add(new Location(269,441,"Water Inner East",new RGB(35,27,175)));
		locations.add(new Location(275,444,"Water Main East",new RGB(35,27,170)));
		locations.add(new Location(304,426,"Water North East Buildings",new RGB(35,27,165)));
		locations.add(new Location(237,411,"Water North West Connector",new RGB(35,27,160)));
		locations.add(new Location(234,423,"Water North West Edge",new RGB(35,27,155)));
		locations.add(new Location(224,419,"Water North West Horseshoe",new RGB(35,27,155)));
		// Market
		locations.add(new Location(237,323,"Market East",new RGB(26,55,111)));
		locations.add(new Location(222,323,"Market West",new RGB(26,55,105)));
		locations.add(new Location(210,326,"Market Road East",new RGB(26,55,100)));
		locations.add(new Location(200,331,"Market Road West",new RGB(26,55,95)));
		// South of Market
		locations.add(new Location(222,351,"South of Market Triple Bins",new RGB(35,27,120)));
		locations.add(new Location(214,357,"South of Market Connected",new RGB(35,27,115)));
		locations.add(new Location(205,364,"South of Market Balloon",new RGB(35,27,110)));
		// Skulltown
		locations.add(new Location(164,371,"Skulltown South East",new RGB(26,55,75)));
		locations.add(new Location(167,348,"Skulltown North East",new RGB(26,55,65)));
		locations.add(new Location(134,344,"Skulltown North West",new RGB(26,55,55)));
		locations.add(new Location(142,368,"Skulltown South West",new RGB(26,55,45)));
		locations.add(new Location(152,359,"Skulltown South Building",new RGB(26,55,15)));
		locations.add(new Location(157,353,"Skulltown East Building",new RGB(26,55,25)));
		locations.add(new Location(145,351,"Skulltown North Building",new RGB(26,55,35)));
		// Thunderdome
		locations.add(new Location(114,389,"Thunderdome North East",new RGB(120,62,250)));
		locations.add(new Location(117,411,"Thunderdome East",new RGB(105,62,250)));
		locations.add(new Location(89,393,"Thunderdome North",new RGB(115,62,250)));
		locations.add(new Location(100,406,"Thunderdome Center",new RGB(110,62,250)));
		// East of Thunderdome
		locations.add(new Location(185,416,"East of Thunder East Shacks",new RGB(85,62,250)));
		locations.add(new Location(159,419,"East of Thunder North East",new RGB(90,62,250)));
		locations.add(new Location(145,414,"East of Thunder Dirt Floor",new RGB(100,62,250)));
		locations.add(new Location(147,429,"East of Thunder South",new RGB(95,62,250)));
		// North of Thunderdome
		locations.add(new Location(85,373,"North of Thunder South Shacks",new RGB(125,62,250)));
		locations.add(new Location(77,341,"North of Thunder Road West",new RGB(130,62,250)));
		locations.add(new Location(92,338,"North of Thunder Road East",new RGB(135,62,250)));
		// Bridges
		locations.add(new Location(285,261,"Bridges High Ground",new RGB(155,62,250)));
		locations.add(new Location(287,286,"Bridges Low Ground",new RGB(150,62,250)));
		locations.add(new Location(277,274,"Bridges East Bridge",new RGB(145,62,250)));
		locations.add(new Location(265,286,"Bridges West Bridge",new RGB(140,62,250)));
		// North of Skulltown
		locations.add(new Location(169,304,"North of Skulltown East Main",new RGB(26,55,90)));
		locations.add(new Location(125,304,"North of Skulltown West Outpost",new RGB(26,55,85)));
		// Airbase
		locations.add(new Location(44,286,"Airbase South Strip",new RGB(205,62,250)));
		locations.add(new Location(65,286,"Airbase Main South",new RGB(210,62,250)));
		locations.add(new Location(70,259,"Airbase Main Entrance",new RGB(215,62,250)));
		locations.add(new Location(57,243,"Airbase Main North",new RGB(225,62,250)));
		locations.add(new Location(35,254,"Airbase North Strip",new RGB(220,62,250)));
		// "Town"
		locations.add(new Location(97,232,"Town North West",new RGB(235,62,250)));
		locations.add(new Location(97,244,"Town South West",new RGB(230,62,250)));
		locations.add(new Location(109,232,"Town Lower Bridge",new RGB(240,62,250)));
		locations.add(new Location(119,232,"Town North East",new RGB(255,62,250)));
		locations.add(new Location(117,242,"Town South East",new RGB(245,62,250)));
		// Bunker
		locations.add(new Location(144,224,"Bunker West",new RGB(200,62,250)));
		locations.add(new Location(159,222,"Bunker Main",new RGB(195,62,250)));
		locations.add(new Location(174,226,"Bunker East",new RGB(190,62,250)));
		// Bunker Lagoon
		locations.add(new Location(190,217,"Bunk Lagoon Water House North",new RGB(185,62,250)));
		locations.add(new Location(182,237,"Bunk Lagoon Water House South",new RGB(165,62,250)));
		locations.add(new Location(195,232,"Bunk Lagoon Connected",new RGB(180,62,250)));
		locations.add(new Location(220,232,"Bunk Lagoon Mountain Trail",new RGB(175,62,250)));
		locations.add(new Location(187,259,"Bunk Lagoon Cave Exit West",new RGB(160,62,250)));
		locations.add(new Location(210,264,"Bunk Lagoon Cave Exit East",new RGB(165,62,250)));
		// Two Spines (Forest South of Artillery)
		locations.add(new Location(306,129,"Two Spines North East",new RGB(130,135,206)));
		locations.add(new Location(284,149,"Two Spines Forest",new RGB(130,130,206)));
		locations.add(new Location(299,199,"Two Spines Connected Sewer",new RGB(118,200,154)));
		locations.add(new Location(269,199,"Two Spines Triple Shack",new RGB(118,205,154)));
		// Artillery
		locations.add(new Location(282,93,"Artillery Main Entrance",new RGB(199,180,226)));
		locations.add(new Location(274,63,"Artillery East Building",new RGB(199,200,226)));
		locations.add(new Location(257,51,"Artillery North Building",new RGB(199,195,226)));
		locations.add(new Location(239,63,"Artillery West Building",new RGB(199,205,226)));
		locations.add(new Location(257,81,"Artillery Bunkers",new RGB(199,190,226)));
		locations.add(new Location(206,76,"Artillery Big Tunnel",new RGB(199,185,226)));
		// Cascades
		locations.add(new Location(219,119,"Cascades Control Tower",new RGB(130,140,206)));
		locations.add(new Location(222,166,"Cascades East",new RGB(121,205,207)));
		locations.add(new Location(192,164,"Cascades West",new RGB(121,210,207)));
		locations.add(new Location(187,201,"Cascades High Ground",new RGB(121,200,207)));
		locations.add(new Location(184,119,"Cascades North West",new RGB(121,215,207)));
		locations.add(new Location(157,109,"Cascades Overlook Beacon",new RGB(121,195,207)));
		locations.add(new Location(156,184,"Cascades Connector",new RGB(175,136,136)));
		// The Pit
		locations.add(new Location(113,161,"The Pit",new RGB(190,136,136)));
		locations.add(new Location(121,187,"Gold House",new RGB(185,136,136)));
		// Runoff
		locations.add(new Location(66,172,"Runoff North",new RGB(165,136,136)));
		locations.add(new Location(64,187,"Runoff Middle",new RGB(155,136,136)));
		locations.add(new Location(61,207,"Runoff South",new RGB(150,136,136)));
		locations.add(new Location(31,187,"Runoff Far West",new RGB(145,136,136)));
		// Slum Lakes
		locations.add(new Location(116,81,"Slums North Cave",new RGB(220,136,136)));
		locations.add(new Location(111,116,"Slums Balloon",new RGB(210,136,136)));
		locations.add(new Location(91,106,"Slums High Ground",new RGB(205,136,136)));
		locations.add(new Location(66,101,"Slums North",new RGB(200,136,136)));
		locations.add(new Location(66,121,"Slums South",new RGB(195,136,136)));
	}
}
