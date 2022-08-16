/*
 * MIT License
 *
 * Copyright (c) 2019 Matt
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package pl.aftermc.bans.lib.mf.base;

import pl.aftermc.bans.lib.mf.base.components.MessageResolver;
import pl.aftermc.bans.lib.mf.exceptions.MfException;
import org.bukkit.command.CommandSender;
import pl.aftermc.bans.util.ChatUtil;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("WeakerAccess")
public final class MessageHandler {

    // The map with the messages to send.
    private final Map<String, MessageResolver> messages = new HashMap<>();

    // Registers all the default messages.
    MessageHandler() {
        register("cmd.no.permission", sender -> sender.sendMessage(ChatUtil.fixColor("&cNie masz uprawnień do wykonania tego polecenia!")));
        register("cmd.no.console", sender -> sender.sendMessage(ChatUtil.fixColor("&cPolecenie nie może być wykonane przez konsolę!")));
        register("cmd.no.player", sender -> sender.sendMessage(ChatUtil.fixColor("&cPolecenie można wykonać tylko za pomocą konsoli!")));
        register("cmd.no.exists", sender -> sender.sendMessage(ChatUtil.fixColor("&cPolecenie, którego próbujesz użyć, nie istnieje!")));
        register("cmd.wrong.usage", sender -> sender.sendMessage(ChatUtil.fixColor("&cNiewłaściwe użycie polecenia!")));
    }

    /**
     * Method to register new messages and overwrite the existing ones.
     *
     * @param messageId       The message ID to be set.
     * @param messageResolver The message resolver function.
     */
    public void register(final String messageId, final MessageResolver messageResolver) {
        messages.put(messageId, messageResolver);
    }

    boolean hasId(String messageId) {
        return messages.get(messageId) != null;
    }

    /**
     * Sends the registered message to the command sender.
     *
     * @param messageId The message ID.
     * @param sender    The command sender to send the message to.
     */
    void sendMessage(final String messageId, final CommandSender sender) {
        final MessageResolver messageResolver = messages.get(messageId);
        if (messageResolver == null) throw new MfException("The message ID \"" + messageId + "\" does not exist!");
        messageResolver.resolve(sender);
    }

}
