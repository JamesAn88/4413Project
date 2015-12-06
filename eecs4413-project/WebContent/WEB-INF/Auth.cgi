#!/usr/bin/perl -w
use CGI qw(:all);
use CGI::Carp qw(fatalsToBrowser);
use strict; 
#################################

my $date = `date`;
my $dirListing = `ls`;
my $value;
my $secret = "secret";

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