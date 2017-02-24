require 'buildr_plus'
BuildrPlus::FeatureManager.activate_features([:oss, :travis])
require 'buildr_plus/projects/java_multimodule'

BuildrPlus::Roles.project('router') do
  project.comment = 'router: A play project to test routing'
  project.group = 'router'
end

BuildrPlus::Roles.role(:user_experience) do
  compile.with BuildrPlus::Libs.gwt_lognice, 'com.google.gwt:elemental2-experimental:jar:16-06-30'
end

require 'buildr_plus/activate'
