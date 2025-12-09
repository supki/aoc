{ pkgs ? import <nixpkgs> {}
}:

pkgs.mkShell {
  buildInputs = with pkgs; [
    leiningen
  ];

  shellHook = ''
  '';
}
