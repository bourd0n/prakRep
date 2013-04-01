# Import the corpus and functions used from nltk library
import os, sys
from nltk.corpus import brown
from nltk.corpus import cess_esp
from nltk.probability import LidstoneProbDist
from nltk.model import NgramModel
from nltk.tokenize import word_tokenize # Tokenizer

if __name__ == "__main__":

#add language
    tokens1 = list(brown.words())
    tokens1.extend(list(cess_esp.words()))

# estimator for smoothing the N-gram model
    estimator1 = lambda fdist, bins: LidstoneProbDist(fdist, 0.2)

# N-gram language model with 3-grams
    model = NgramModel(3, tokens1, estimator=estimator1)


    twit = sys.argv[1]
    posVars = sys.argv[2]
    pos = sys.argv[3]


    tokens1 = word_tokenize(twit)
    tokens2 = word_tokenize(posVars)

    posI = int(pos)
    perp = ""
    res = ""

    for element in tokens2:
        tokens1[posI] = element
        str = ' '.join(tokens1)
        perplexity = model.perplexity(tokens1)
        print str + " " + "%f" % float(perplexity)
        if perplexity < perp :
            perp = perplexity
            res = element

# print the text
    print '===='
    print twit
    print "perp: " + "%f" % float(perp)
    print " result: " + res
    print res


