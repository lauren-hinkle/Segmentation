#!/bin/bash

NAME=HinkleProposal

if [[ "$1" == "clean" ]]; then
    echo "Cleaning"
    rm -rf *.aux *.bbl *.blg *.log *.pdf
else
    echo "Building $NAME"
    pdflatex $NAME.tex
    bibtex $NAME
    pdflatex $NAME.tex
    pdflatex $NAME.tex
fi