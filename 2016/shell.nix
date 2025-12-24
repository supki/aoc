{ pkgs ? import <nixpkgs> {}
}:

pkgs.mkShell {
  buildInputs = with pkgs; [
    leiningen
  ];

  shellHook = ''
    # Java stacktraces are extremely useful and totally
    # not a waste of space.
    export JVM_OPTS="-XX:MaxJavaStackTraceDepth=3"
  '';
}
