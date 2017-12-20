package com.avairebot.orion.plugin;

import com.avairebot.orion.Orion;
import com.avairebot.orion.exceptions.InvalidPluginException;
import com.avairebot.orion.exceptions.InvalidPluginsPathException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class PluginManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(PluginManager.class);

    private final Orion orion;
    private final File pluginsFolder;

    private final Set<PluginLoader> plugins = new HashSet<>();

    public PluginManager(Orion orion) {
        this.orion = orion;
        this.pluginsFolder = new File("plugins");

        if (!pluginsFolder.exists()) {
            pluginsFolder.mkdirs();
        }
    }

    public void loadPlugins() throws InvalidPluginsPathException, InvalidPluginException {
        if (!pluginsFolder.isDirectory() || pluginsFolder.listFiles() == null) {
            throw new InvalidPluginsPathException("Invalid plugins path exception, the plugins path is not a directory.");
        }

        for (File file : pluginsFolder.listFiles()) {
            try {
                LOGGER.debug("Attempting to load plugin: " + file.toString());
                PluginLoader pluginLoader = new PluginLoader(file, pluginsFolder);

                plugins.add(pluginLoader);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public Set<PluginLoader> getPlugins() {
        return plugins;
    }
}
