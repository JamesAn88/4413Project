#!/usr/bin/perl -w
use CGI qw(:all);
use CGI::Carp qw(fatalsToBrowser);
use strict; 
#################################

my $date = `date`;
my $dirListing = `ls`;
my $value;
my $secret = "11850539574803748954511465232603835743288401123108386029232010505137480652464235096705921628410148003499568874609717247628022206165767457311981401642693902";

my $q = CGI->new;
my @params = $q->param;

my $ip = $ENV{REMOTE_ADDR};
my $port = $ENV{REMOTE_PORT};
my $requestType = $ENV{REQUEST_METHOD};
my $user = $ENV{REMOTE_USER};

my $userHash = `echo -n "$user$secret" | sha1sum | cut -f1 -d" "`;
my $backParam = $q->param("back");
my $burl="$backParam?user=$user\&hash=$userHash";

print "Location: $burl\n\n";
print "Content-type: text/html\n\n";