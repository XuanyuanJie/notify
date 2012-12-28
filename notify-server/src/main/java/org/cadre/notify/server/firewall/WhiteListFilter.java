/**
 * ispt team
 * Copyright (c) 2011-2011 All Rights Reserved.
 */
package org.cadre.notify.server.firewall;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.mina.core.filterchain.IoFilterAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.core.write.WriteRequest;
import org.apache.mina.filter.firewall.Subnet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author ÐùÔ¯
 * @version $Id: WhiteListFilter.java, v 0.1 2011-11-1 ÏÂÎç03:00:25 ÐùÔ¯ Exp $
 */
public class WhiteListFilter extends IoFilterAdapter {
    private final List<Subnet>  whitelist = new CopyOnWriteArrayList<Subnet>();

    private final static Logger LOGGER    = LoggerFactory.getLogger(WhiteListFilter.class);

    /**
     * Sets the addresses to be WhiteListed.
     *
     * NOTE: this call will remove any previously WhiteListed addresses.
     *
     * @param addresses an array of addresses to be WhiteListed.
     */
    public void setWhitelist(InetAddress[] addresses) {
        if (addresses == null) {
            throw new IllegalArgumentException("addresses");
        }
        whitelist.clear();
        for (int i = 0; i < addresses.length; i++) {
            InetAddress addr = addresses[i];
            block(addr);
        }
    }

    /**
     * Sets the subnets to be WhiteListed.
     *
     * NOTE: this call will remove any previously WhiteListed subnets.
     *
     * @param subnets an array of subnets to be WhiteListed.
     */
    public void setSubnetwhitelist(Subnet[] subnets) {
        if (subnets == null) {
            throw new IllegalArgumentException("Subnets must not be null");
        }
        whitelist.clear();
        for (Subnet subnet : subnets) {
            block(subnet);
        }
    }

    /**
     * Sets the addresses to be WhiteListed.
     *
     * NOTE: this call will remove any previously WhiteListed addresses.
     *
     * @param addresses a collection of InetAddress objects representing the
     *        addresses to be WhiteListed.
     * @throws IllegalArgumentException if the specified collections contains
     *         non-{@link InetAddress} objects.
     */
    public void setWhitelist(Iterable<InetAddress> addresses) {
        if (addresses == null) {
            throw new IllegalArgumentException("addresses");
        }

        whitelist.clear();

        for (InetAddress address : addresses) {
            block(address);
        }
    }

    /**
     * Sets the subnets to be WhiteListed.
     *
     * NOTE: this call will remove any previously WhiteListed subnets.
     *
     * @param subnets an array of subnets to be WhiteListed.
     */
    public void setSubnetwhitelist(Iterable<Subnet> subnets) {
        if (subnets == null) {
            throw new IllegalArgumentException("Subnets must not be null");
        }
        whitelist.clear();
        for (Subnet subnet : subnets) {
            block(subnet);
        }
    }

    /**
     * Blocks the specified endpoint.
     */
    public void block(InetAddress address) {
        if (address == null) {
            throw new IllegalArgumentException("Adress to block can not be null");
        }

        block(new Subnet(address, 32));
    }

    /**
     * Blocks the specified subnet.
     */
    public void block(Subnet subnet) {
        if (subnet == null) {
            throw new IllegalArgumentException("Subnet can not be null");
        }

        whitelist.add(subnet);
    }

    /**
     * Unblocks the specified endpoint.
     */
    public void unblock(InetAddress address) {
        if (address == null) {
            throw new IllegalArgumentException("Adress to unblock can not be null");
        }

        unblock(new Subnet(address, 32));
    }

    /**
     * Unblocks the specified subnet.
     */
    public void unblock(Subnet subnet) {
        if (subnet == null) {
            throw new IllegalArgumentException("Subnet can not be null");
        }
        whitelist.remove(subnet);
    }

    @Override
    public void sessionCreated(NextFilter nextFilter, IoSession session) {
        if (!isBlocked(session)) {
            // forward if not blocked
            nextFilter.sessionCreated(session);
        } else {
            blockSession(session);
        }
    }

    @Override
    public void sessionOpened(NextFilter nextFilter, IoSession session) throws Exception {
        if (!isBlocked(session)) {
            // forward if not blocked
            nextFilter.sessionOpened(session);
        } else {
            blockSession(session);
        }
    }

    @Override
    public void sessionClosed(NextFilter nextFilter, IoSession session) throws Exception {
        if (!isBlocked(session)) {
            // forward if not blocked
            nextFilter.sessionClosed(session);
        } else {
            blockSession(session);
        }
    }

    @Override
    public void sessionIdle(NextFilter nextFilter, IoSession session, IdleStatus status)
                                                                                        throws Exception {
        if (!isBlocked(session)) {
            // forward if not blocked
            nextFilter.sessionIdle(session, status);
        } else {
            blockSession(session);
        }
    }

    @Override
    public void messageReceived(NextFilter nextFilter, IoSession session, Object message) {
        if (!isBlocked(session)) {
            // forward if not blocked
            nextFilter.messageReceived(session, message);
        } else {
            blockSession(session);
        }
    }

    @Override
    public void messageSent(NextFilter nextFilter, IoSession session, WriteRequest writeRequest)
                                                                                                throws Exception {
        if (!isBlocked(session)) {
            // forward if not blocked
            nextFilter.messageSent(session, writeRequest);
        } else {
            blockSession(session);
        }
    }

    private void blockSession(IoSession session) {
        LOGGER.warn("Remote address in the whitelist; closing.");
        session.close(true);
    }

    private boolean isBlocked(IoSession session) {
        SocketAddress remoteAddress = session.getRemoteAddress();
        if (remoteAddress instanceof InetSocketAddress) {
            InetAddress address = ((InetSocketAddress) remoteAddress).getAddress();

            // check all subnets
            for (Subnet subnet : whitelist) {
                if (!subnet.inSubnet(address)) {
                    return true;
                }
            }
        }

        return false;
    }
}
