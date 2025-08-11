package me.vivekanand.crayschat.model

import me.vivekanand.crayschat.protocol.CraysChatPacket

/**
 * Represents a routed packet with additional metadata
 * Used for processing and routing packets in the mesh network
 */
data class RoutedPacket(
    val packet: CraysChatPacket,
    val peerID: String? = null,           // Who sent it (parsed from packet.senderID)
    val relayAddress: String? = null      // Address it came from (for avoiding loopback)
)