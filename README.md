# Router: Experiments with a raw gwt router

[![Build Status](https://secure.travis-ci.org/realityforge/gwt-router.png?branch=master)](http://travis-ci.org/realityforge/gwt-router)

## What is Router?

A project that experiments with basic routing under gwt. First based on angule, then react-router and now
trying to use router5.

## TODO:

* If router parameters are mapped to fields then updating fields should update url.
* Write annotation processor that generates and configures 1 or more router managers based on the presence of annotations.
* Support routes that dynamically load routes. i.e. Add a route that dynamically loads a set of routes and replaces itself with them.
* Support either child or peer routers. These routers pick up optional routes that are suffixed to the main route and delegated to separate components. Typically these will be things like popup dialogs that contain some state.
