(defproject clojure-basics "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :dependencies [[org.clojure/clojure "1.11.1"]
                 [ring-server "0.5.0"]
                 [reagent "1.1.1"]
                 [reagent-utils "0.3.4"]
                 [cljsjs/react "17.0.2-0"]
                 [cljsjs/react-dom "17.0.2-0"]
                 [ring "1.9.5"]
                 [ring/ring-defaults "0.3.3"]
                 [com.datomic/datomic-free "0.9.5697"]
                 [hiccup "1.0.5"]
                 [yogthos/config "1.2.0"]
                 [metosin/reitit "0.5.18"]
                 [pez/clerk "1.0.0"]
                 [venantius/accountant "0.2.5"
                  :exclusions [org.clojure/tools.reader]]
                 [vvvvalvalval/datomock "0.2.2"]]

  :jvm-opts ["-Xmx1G"]

  :plugins [[lein-environ "1.1.0"]
            [lein-cljsbuild "1.1.7"]
            [lein-asset-minifier "0.4.6"
             :exclusions [org.clojure/clojure]]]

  :ring {:handler clojure-basics.handler/app
         :uberwar-name "clojure-basics.war"}

  :min-lein-version "2.5.0"
  :uberjar-name "clojure-basics.jar"
  :main clojure-basics.server
  :clean-targets ^{:protect false}
  [:target-path
   [:cljsbuild :builds :app :compiler :output-dir]
   [:cljsbuild :builds :app :compiler :output-to]]

  :source-paths ["src/clj" "src/cljc"]
  :test-paths ["test/clj/clojure-basics"]
  :resource-paths ["resources" "target/cljsbuild"]
  :profiles {:dev {:repl-options {:init-ns clojure-basics.repl}
                   :dependencies [[cider/piggieback "0.5.3"]
                                  [binaryage/devtools "1.0.6"]
                                  [ring/ring-mock "0.4.0"]
                                  [ring/ring-devel "1.9.5"]
                                  [prone "2021-04-23"]
                                  [nrepl "0.9.0"]
                                  [thheller/shadow-cljs "2.16.7"]
                                  [pjstadig/humane-test-output "0.11.0"]]

                   :source-paths ["env/dev/clj"]
                   :injections [(require 'pjstadig.humane-test-output)
                                (pjstadig.humane-test-output/activate!)]

                   :env {:dev true}}
             :uberjar {:hooks [minify-assets.plugin/hooks]
                       :source-paths ["env/prod/clj"]
                       :env {:production true}
                       :aot :all
                       :omit-source true}})
