package hudson.plugins.graven;

import hudson.Extension;
import hudson.model.AbstractProject;
import hudson.model.FreeStyleProject;
import hudson.tasks.BuildStepDescriptor;
import hudson.tasks.Builder;

import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.StaplerRequest;

import net.sf.json.JSONObject;

/**
 * @author <a href="mailto:nicolas.deloof@cloudbees.com">Nicolas De loof</a>
 */
public class GravenBuilder extends Builder {

    private final String task;

    public String getTask() {
        return task;
    }

    @DataBoundConstructor
    public GravenBuilder(String task) {
        this.task = task;
    }


    public boolean isEnableCloud() {
        return getDescriptor().isEnableCloud();
    }

    @Override
    public Descriptor getDescriptor() {
        return (Descriptor) super.getDescriptor();
    }

    @Extension
    public static class Descriptor extends BuildStepDescriptor<Builder> {

        private boolean enableCloud;

        public Descriptor() {
            load();
        }

        public boolean isEnableCloud() {
            return enableCloud;
        }

        public void setEnableCloud(boolean enableCloud) {
            this.enableCloud = enableCloud;
        }

        @Override
        public boolean configure(StaplerRequest req, JSONObject json) throws FormException {
            req.bindJSON(this, json.getJSONObject("graven"));
            save();
            return true;
        }

        @Override
        public boolean isApplicable(Class<? extends AbstractProject> jobType) {
            return FreeStyleProject.class.isAssignableFrom(jobType);
        }

        @Override
        public Builder newInstance(StaplerRequest req, JSONObject formData) throws FormException {
            return super.newInstance(req, formData);    //To change body of overridden methods use File | Settings | File Templates.
        }

        @Override
        public String getDisplayName() {
            return Messages.GravenBuilder_Task();
        }
    }
}
