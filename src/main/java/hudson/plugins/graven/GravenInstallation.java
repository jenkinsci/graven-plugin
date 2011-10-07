package hudson.plugins.graven;

import hudson.EnvVars;
import hudson.Extension;
import hudson.model.EnvironmentSpecific;
import hudson.model.Node;
import hudson.model.TaskListener;
import hudson.slaves.NodeSpecific;
import hudson.tools.ToolDescriptor;
import hudson.tools.ToolInstallation;
import hudson.tools.ToolProperty;

import java.io.IOException;
import java.util.List;

import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.StaplerRequest;

import net.sf.json.JSONObject;

/**
 * @author <a href="mailto:nicolas.deloof@cloudbees.com">Nicolas De loof</a>
 */
public class GravenInstallation extends ToolInstallation
       implements NodeSpecific<GravenInstallation>, EnvironmentSpecific<GravenInstallation> {

    @DataBoundConstructor
    public GravenInstallation(String name, String home, List<? extends ToolProperty<?>> properties) {
        super(name, home, properties);
    }

    public GravenInstallation forEnvironment(EnvVars environment) {
        return new GravenInstallation(getName(), environment.expand(getHome()), getProperties().toList());
    }

    public GravenInstallation forNode(Node node, TaskListener log) throws IOException, InterruptedException {
        return new GravenInstallation(getName(), translateFor(node, log), getProperties().toList());
    }

    @Extension
    public static final class Descriptor extends ToolDescriptor<GravenInstallation> {

        public Descriptor() {
            setInstallations();
            load();
        }

        @Override
        public String getDisplayName() {
            return "GRaveN";
        }

        @Override
        public GravenInstallation newInstance(StaplerRequest req, JSONObject formData) throws FormException {
            return (GravenInstallation) super.newInstance(req, formData.getJSONObject("graven"));
        }
    }
}
